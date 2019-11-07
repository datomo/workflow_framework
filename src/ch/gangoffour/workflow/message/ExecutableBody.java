package ch.gangoffour.workflow.message;

import java.util.function.Consumer;

public class ExecutableBody<T, D> {

    public final Consumer<? super T> f;
    public final D data;

    public ExecutableBody (Consumer<? super T> f, D data){
        this.f = f;
        this.data = data;
    }

    public ExecutableBody<T, D> changeFunction(Consumer<? super T> f){
        return new ExecutableBody<>(f, this.data);
    }

    public ExecutableBody<T, D> changeData(D data){
        return new ExecutableBody<>(this.f, data);
    }

    public void execute(T input) {
        f.accept(input);
    }
}
