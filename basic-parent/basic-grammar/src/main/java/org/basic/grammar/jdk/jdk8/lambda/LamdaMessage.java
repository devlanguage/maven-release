package org.basic.grammar.jdk.jdk8.lambda;

public class LamdaMessage {
    public String name;
    public String color;
    public long delay;

    public LamdaMessage(String name_, String color_, long delay_) {
        this.name = name_;
        this.color = color_;
        this.delay = delay_;
    }

    public String toUpperCase() {
        return "name=" + name.toUpperCase() + ",type=" + color.toUpperCase() + ",delay=" + this.delay;
    }
    @Override
    public String toString() {
        return "name=" + name + ",type=" + color + ",delay=" + this.delay;
    }
}
