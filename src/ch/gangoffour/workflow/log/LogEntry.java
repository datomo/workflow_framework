package ch.gangoffour.workflow.log;

import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogEntry {
    enum NodeType {
        TRANSFORMER,
        SPLITTER,
        MERGER,
        JOINER,
        BRANCHER
    }

    public final NodeType type;
    public final Map<String, String> inputs;
    public final Map<String, String> outputs;
    public final String message;

    public LogEntry(NodeType type, Map<String, String> inputs, Map<String, String> outputs, String message) {
        this.type = type;
        this.inputs = inputs;
        this.outputs = outputs;
        this.message = message;
    }

    public LogEntry(NodeType type, String input, Map<String, String> outputs, String message) {
        this(type, new HashMap<String, String>() {{
            put("input", input);
        }}, outputs, message);
    }

    public LogEntry(NodeType type, Map<String, String> inputs, String output, String message) {
        this(type, inputs, new HashMap<String, String>() {{
            put("output", output);
        }}, message);
    }

    public LogEntry(NodeType type, String input, String output, String message) {
        this(type, new HashMap<String, String>() {{
            put("input", input);
        }}, new HashMap<String, String>() {{
            put("output", output);
        }}, message);
    }
}
