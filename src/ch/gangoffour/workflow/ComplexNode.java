package ch.gangoffour.workflow;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

public class ComplexNode<T> extends WorkflowNode<T> {

    private final ProxyLogger<T> logger;
    private final Output<T> output;

    ComplexNode(BiFunction<Output<T>, Logger<T>, Output<T>> creator, Output<T> input) {
        super(null);
        logger = new ProxyLogger<>();
        output = creator.apply(input, logger);
    }

    public Output<T> getOutput() {
        return output;
    }

    @Override
    void addLogger(Logger<T> logger) {
        this.logger.addLogger(logger);
    }

    private static class ProxyLogger<T> extends Logger<T> {
        private List<Logger<T>> loggers = new LinkedList<>();

        @Override
        protected void log(LogEntry<T> entry) {
            loggers.forEach(l -> l.log(entry));
        }

        void addLogger(Logger<T> logger) {
            loggers.add(logger);
        }
    }
}
