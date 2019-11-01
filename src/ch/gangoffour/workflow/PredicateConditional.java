package ch.gangoffour.workflow;

import java.util.function.Predicate;

public class PredicateConditional<T> extends BiConditional<T> {

    private final Predicate<T> condition;

    public PredicateConditional(Output<T> input, Predicate<T> condition) {
        super(input);
        this.condition = condition;
    }

    @Override
    boolean takeFirstBranch(T val) {
        return condition.test(val);
    }
}
