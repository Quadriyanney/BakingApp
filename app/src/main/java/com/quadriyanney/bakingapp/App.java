package com.quadriyanney.bakingapp;

import android.app.Application;

import com.quadriyanney.bakingapp.dependencyInjection.component.DaggerDependencyComponent;
import com.quadriyanney.bakingapp.dependencyInjection.component.DependencyComponent;
import com.quadriyanney.bakingapp.dependencyInjection.module.AppModule;
import com.quadriyanney.bakingapp.dependencyInjection.module.DependencyModule;

public class App extends Application {

    private static DependencyComponent dependencyComponent;

    public static DependencyComponent getDependencyComponent() {
        return dependencyComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dependencyComponent = DaggerDependencyComponent
                .builder()
                .appModule(new AppModule(this))
                .dependencyModule(new DependencyModule())
                .build();
    }
}
