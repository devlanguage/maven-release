package org.basic.jdk.jdk8.defaultstaticinterfacemethods.model;

public interface Alarm {

    default String turnAlarmOn() {
        return "Turning the alarm on.";
    }

    default String turnAlarmOff() {
        return "Turning the alarm off.";
    }
}