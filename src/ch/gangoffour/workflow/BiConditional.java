package ch.gangoffour.workflow;

import java.util.HashMap;
import java.util.List;

public abstract class BiConditional<T> extends WorkflowNode<T> {
    public static class Result<T> {
        final T o1;
        final T o2;

        public Result(T o1, T o2) {
            this.o1 = o1;
            this.o2 = o2;
        }
    }

    final private Output<T> outputTrue;
    final private Output<T> outputFalse;

    protected BiConditional(Output<T> input) {
        super(NodeType.BRANCHER);
        List<Output<T>> outputs = input.conditionalSplit(2, val -> {
            return takeFirstBranchAux(val) ? 0 : 1;
        });

        outputTrue = outputs.get(0);
        outputFalse = outputs.get(1);
    }

    private boolean takeFirstBranchAux(T val) {
        boolean takeFirst = takeFirstBranch(val);
        HashMap<String, T> outputs = new HashMap<>();
        outputs.put("output " + (takeFirst ? "true" : "false"), val);
        log(val, outputs);
        return takeFirst;
    }

    public Output<T> getOutputTrue() {
        return outputTrue;
    }

    public Output<T> getOutputFalse() {
        return outputFalse;
    }

    abstract boolean takeFirstBranch(T input);
}
