package ch.gangoffour.workflow;

import java.util.Arrays;

public abstract class Logger<T> {
    protected abstract void log(LogEntry<T> entry);

    public void log(WorkflowNode... nodes) {
        Arrays.asList(nodes).forEach(n -> n.addLogger(this));
    }
}
