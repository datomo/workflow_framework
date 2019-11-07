package ch.gangoffour.workflow.log;

import ch.gangoffour.workflow.LogEntry;
import ch.gangoffour.workflow.Logger;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Function;

public class PrintLogger<T> extends Logger<T> {

    private final Function<T, String> toString;
    private final PrintStream stream;

    public PrintLogger(Function<T, String> toString, PrintStream stream) {
        this.toString = toString;
        this.stream = stream;
    }

    @Override
    protected void log(LogEntry<T> entry) {
        stream.println();
        stream.println("====================");
        stream.println(entry.type);

        stream.println();
        stream.println("inputs: ");
        for (String key: entry.inputs.keySet()) {
            stream.println(" " + key + ": " + toString.apply(entry.inputs.get(key)));
        }
        stream.println();
        stream.println("outputs: ");
        for (String key: entry.outputs.keySet()) {
            stream.println(" " + key + ": " + toString.apply(entry.outputs.get(key)));
        }
        stream.println();
        stream.println(entry.message);
        stream.println("====================");
    }
}
