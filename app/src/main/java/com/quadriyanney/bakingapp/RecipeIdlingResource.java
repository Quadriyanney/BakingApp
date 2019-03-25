package com.quadriyanney.bakingapp;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback resourceCallback;

    private AtomicBoolean isIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIsIdleState(boolean idle) {
        isIdle.set(idle);
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
