package jarmandocordova.restdemo.demo.global;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import jarmandocordova.restdemo.demo.global.di.component.DaggerMyAppComponent;
import jarmandocordova.restdemo.demo.global.di.component.MyAppComponent;
import jarmandocordova.restdemo.demo.global.di.module.ApplicationModule;
/**
 * Custom application definition.
 * <p/>
 * Created by mgouline on 23/04/15.
 */
public class MyApp extends Application {
    private MyAppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerMyAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mComponent.inject(this);
    }

    public MyAppComponent getComponent() {
        return mComponent;
    }

    /**
     * Extracts application from support context types.
     *
     * @param context Source context.
     * @return Application instance or {@code null}.
     */
    public static MyApp from(@NonNull Context context) {
        return (MyApp) context.getApplicationContext();
    }
}
