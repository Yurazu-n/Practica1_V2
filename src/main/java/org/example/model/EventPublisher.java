package org.example.model;

import org.example.control.MyExecutionException;

public interface EventPublisher {
    void publishEvent(Weather weather) throws MyExecutionException;
}
