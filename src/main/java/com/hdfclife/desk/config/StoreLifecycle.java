package com.hdfclife.desk.config;

import com.hdfclife.desk.store.PolicyStore;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class StoreLifecycle implements SmartLifecycle {

    private final PolicyStore policyStore;

    private boolean running = false;

    public StoreLifecycle(PolicyStore policyStore) {
        this.policyStore = policyStore;
    }

    @Override
    public void start() {
        running = true;
        System.out.println("PolicyStore ready");
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}