package org.third.spring.config.bean_wire;

public class CmdRepService_Original {

    public void businessMethod(Object commandState) {
        System.out.println(this.getClass().getSimpleName() + ".businessMethod(Map commandState() is called");
    }

}
