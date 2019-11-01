package ch.gangoffour.workflow;

public abstract class Transformer<T> {
  final private Output<T> output;

  public Transformer<T>(Output<T> input) {
    output = input.map(this::transform);
  }

  protected abstract T transform(T value);
}