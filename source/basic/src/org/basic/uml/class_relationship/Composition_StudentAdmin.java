package org.basic.uml.class_relationship;

public class Composition_StudentAdmin {

    static class Computer {
        private String cpu;
        private float weight;
        private Monitor aMonitor;

        public Computer(String cpu, float weight, int inch, boolean isFlat) {

            this.cpu = cpu;
            this.weight = weight;
            //if aggregation association relationship, Monitor will be created by Client
            this.aMonitor = new Monitor(inch, isFlat);
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

        // Monitor aMonitor=new Monitor(17, true);
        Computer aComputer = new Computer("486", 32.0F, 17, true);
        System.out.println("Computer is :" + aComputer);
        aComputer.turnOn();
    }
}
