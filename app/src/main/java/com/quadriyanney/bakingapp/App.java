package com.quadriyanney.bakingapp;

import android.app.Application;

import com.quadriyanney.bakingapp.dependencyInjection.component.DaggerDependencyComponent;
import com.quadriyanney.bakingapp.dependencyInjection.component.DependencyComponent;
import com.quadriyanney.bakingapp.dependencyInjection.module.AppModule;

public class App extends Application {

    private static DependencyComponent dependencyComponent;
    private static App app;

    public static DependencyComponent getDependencyComponent() {
        return dependencyComponent;
    }

    public static App getAppInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = new App();

        dependencyComponent = DaggerDependencyComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
