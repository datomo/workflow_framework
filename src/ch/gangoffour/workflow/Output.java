package ch.gangoffour.workflow;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Output<T> {
    static class Token {
        static int highestHash = 0;
        private int hash;

        Token() {
            hash = highestHash++;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Token
                    && ((Token) obj).hash == hash;
        }
    }

    private Map<Token,Queue<T>> queues = new HashMap<>();
    private Map<Token,Consumer<Queue<T>>> listeners = new HashMap<>();

    Output(Consumer<Consumer<T>> send) {
        send.accept(this::receiveValue);
    }

    public Output<T> merge(List<Output<T>> inputs) {
        return new Output<>(sender -> {
            inputs.forEach(in -> {
                waitFor(Collections.singletonList(in), l -> {
                    sender.accept(l.get(0));
                });
            });
        });
    }

    public List<Output<T>> conditionalSplit(int n, Function<T, Integer> decider) {
        List<Output<T>> outputs = new ArrayList<>(n);
        List<Consumer<T>> senders = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            outputs.add(new Output<>(senders::add));
        }

        waitFor(Collections.singletonList(this), l -> {
            senders.get(decider.apply(l.get(0))).accept(l.get(0));
        });

        return outputs;
    }

    public List<Output<T>> split(int n, Function<T, List<T>> f) {
        List<Output<T>> outputs = new ArrayList<>(n);
        List<Consumer<T>> senders = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            outputs.add(new Output<>(senders::add));
        }

        waitFor(Collections.singletonList(this), l -> {
            List<T> results = f.apply(l.get(0));
            for(int i = 0; i < n; i++){
                senders.get(i).accept(results.get(i));
            }
        });

        return outputs;
    }

    /**
     * Apply a given transformation
     * @param f the transformation
     * @return the transformed output
     */
    public Output<T> map(Function<T, T> f) {
        return new Output<>(sender -> {
            waitFor(Collections.singletonList(this), l -> {
                sender.accept(f.apply(l.get(0)));
            });
        });
    }

    /**
     * Combines the given output with an other, by the given condition
     * @param other the used output to combine with
     * @param f the function applied to both combined outputs
     * @return the new combination of the two outputs
     */
    public Output<T> combine(Output<T> other, BinaryOperator<T> f) {
        return new Output<>(sender -> {
           waitFor(Stream.of(this, other).collect(Collectors.toList()), l -> {
               sender.accept(f.apply(l.get(0), l.get(1)));
           });
        });
    }

    static public <T> Output<T> reduce(List<Output<T>> inputs, T start, BinaryOperator<T> f) {
        return new Output<>(sender -> {
            waitFor(inputs, l -> sender.accept( l.stream().reduce(start, f)));
        });
    }

    private void receiveValue(T value) {
        queues.values().forEach(q -> q.add(value));
    }

    void onOutput(Consumer<Queue<T>> listener) {
        final Token token = new Token();
        queues.put(token, new LinkedList<T>());
        listeners.put(token, listener);
    }

    static <T> void waitFor(int n, List<Output<T>> outputs, Consumer<List<Queue<T>>> listener) {
        List<Queue<T>> queues = Stream.generate(LinkedList<T>::new)
                .limit(outputs.size())
                .collect(Collectors.toList());

        for (int i = 0; i < queues.size(); i++) {
            Output<T> output = outputs.get(i);
            Queue<T> queue = queues.get(i);
            output.onOutput(q -> {
                queue.add(q.remove());
                if (queues.stream().noneMatch(qu -> qu.size() >= n)) {
                    listener.accept(queues);
                }
            });
        }
    }

    static <T> void waitFor(List<Output<T>> outputs, Consumer<List<T>> listener) {
        waitFor(1, outputs, l -> listener.accept(l.stream().map(Queue::remove).collect(Collectors.toList())));
    }
}

/**
 * Output object which can be used by compatible input object
 * @param <T> generic format of the output
 */

