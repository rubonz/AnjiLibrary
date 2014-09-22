package com.darkempire.anji.engine;

/**
 * Created by siredvin on 26.12.13.
 */

public interface Event {
    public static enum EventType {
        UPDATE, DELETE, RESET, STOP, START;
    }

    public EventType getEvent();

    public Object getSource();
}
