# Workflow Framework

This framework is part of a exercise in the "Software Architecture" lecture at university Basel.

## Exercise Answers

The answers to the given exercise can be found in
[ExerciseAnswers](ExcerciseAnswers.md)

## Additional Functionality Example for exe 4
```java
// Example use of our executable message in a simple workflow
FixedSender<Message<Integer, ExecutableBody<String, String>>> messageSender = new FixedSender<>(
        new Message<>(0, new ExecutableBody<>(m -> {
                System.out.println(m);
                sender.start();
            }, "this is the body")));
new ExecutingReceiver<>(messageSender.getOutput(), "Hello, World!");
messageSender.start();
```

## UML

[Link to the UML](Package workflow.png)


## Getting Started

Simple example of our workflow framework using Doubles.

```java
FixedSender<Double> sender = new FixedSender<>(2.00, 3.00, 4.00, -3.5);
Multiplier times2 = new Multiplier(sender.getOutput(),2);
new PrintReceiver<>(times2.getOutput());
sender.start();
```
```java
//output
4.0
6.0
8.0
-7.0
```

Workflow nodes for other types can easily be implemented.
For instance:

```java
public class Reverser extends Transformer<String> {

    public Reverser(Output<String> input){
        super(input);
    }

    @Override
    protected String transform(String value) {
        return new StringBuilder(value).reverse().toString();
    }
}
```

More elaborate example can be found in [main](src/ch/gangoffour/workflow/main/Workflow.java)

### Prerequisites

To run the program java 8 is required.


### Caution

This framework is mostly barebones at the moment so dont expect too much functionality.

## Built With

* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) - Java Language JDK version 8



## Authors

* **Isabel Geissmann** - [isabelge](https://github.com/isabelge)
* **Jannik Jaberg** - [cptunderground](https://github.com/cptunderground)
* **Rik de Graaff** - [overacter](https://github.com/overacter)
* **David Lengweiler** - [datomo](https://github.com/datomo)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


