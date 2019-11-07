package ch.gangoffour.workflow;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkflowNode<T> {
    private final NodeType type;
    private String message;
    private List<Logger<T>> loggers = new LinkedList<>();

    WorkflowNode(NodeType type) {
        this.type = type;
    }

    void log(Map<String, T> inputs, Map<String, T> outputs) {
        LogEntry<T> logEntry = new LogEntry<>(type, inputs, outputs, message);
        logAux(logEntry);
    }

    void log(T input, Map<String, T> outputs) {
        LogEntry<T> logEntry = new LogEntry<>(type, input, outputs, message);
        logAux(logEntry);
    }

    void log(Map<String, T> inputs, T output) {
        LogEntry<T> logEntry = new LogEntry<>(type, inputs, output, message);
        logAux(logEntry);
    }

    void log(T input, T output) {
        LogEntry<T> logEntry = new LogEntry<>(type, input, output, message);
        logAux(logEntry);
    }

    protected void logMessage(String message) {
        this.message = message;
    }

    private void logAux(LogEntry<T> logEntry) {
        this.message = "";
        loggers.forEach(l -> l.log(logEntry));
    }

    void addLogger(Logger<T> logger) {
        loggers.add(logger);
    }
}
