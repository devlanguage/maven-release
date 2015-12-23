package org.third.spring.config.bean_wire;

import java.util.Map;

public class Command {

    public void setState(Map commandState) {

    }

    public Object execute() {

        return null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":hashCode=" + hashCode();
    }
}
