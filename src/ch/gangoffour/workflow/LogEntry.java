package ch.gangoffour.workflow;

import java.util.HashMap;
import java.util.Map;

public class LogEntry<T> {

    public final NodeType type;
    public final Map<String, T> inputs;
    public final Map<String, T> outputs;
    public final String message;

    public LogEntry(NodeType type, Map<String, T> inputs, Map<String, T> outputs, String message) {
        this.type = type;
        this.inputs = inputs;
        this.outputs = outputs;
        this.message = message;
    }

    public LogEntry(NodeType type, T input, Map<String, T> outputs, String message) {
        this(type, new HashMap<String, T>() {{
            put("input", input);
        }}, outputs, message);
    }

    public LogEntry(NodeType type, Map<String, T> inputs, T output, String message) {
        this(type, inputs, new HashMap<String, T>() {{
            put("output", output);
        }}, message);
    }

    public LogEntry(NodeType type, T input, T output, String message) {
        this(type, new HashMap<String, T>() {{
            put("input", input);
        }}, new HashMap<String, T>() {{
            put("output", output);
        }}, message);
    }
}
