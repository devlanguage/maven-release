package org.basic.grammar.uml.class_relationship;

public class Aggregation_StudentAdmin {
    static class Computer {
        private String cpu;
        private float weight;
        private Monitor aMonitor;

        public Computer(String cpu, float weight, Monitor aMonitor) {
            this.cpu = cpu;
            this.weight = weight;
            this.aMonitor = aMonitor;
        }

        public void turnOn() {
            System.out.println("I am on now");
        }
    }

    static class Monitor {

        private int inch;
        private boolean isFlat;

        // no information of computer
        public Monitor(int inch, boolean isFlat) {

            this.inch = inch;
            this.isFlat = isFlat;
        }
    }

    public static void main(String aa[]) {

        Monitor aMonitor = new Monitor(17, true);
        System.out.println("I do something others here");
        Computer aComputer = new Computer("486", 32.0F, aMonitor);
        System.out.println("Computer is :" + aComputer);
        aComputer.turnOn();
    }
}
