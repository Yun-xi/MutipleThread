package com.example.demo.java.design_pattern.observer;

public interface LifeCycleListener {
    void onEvent(ObservableRunnable.RunnableEvent event);
}
