package com.portifolio.magnum.pomodoroapp;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by magnum on 12/16/16.
 */
public class PomodoroApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));

    }

}
