package com.flybits.conciergejavaexample;

import com.flybits.commons.library.api.FlybitsManager;
import com.flybits.commons.library.api.Region;
import com.flybits.commons.library.logging.VerbosityLevel;
import com.flybits.context.ContextManager;
import com.flybits.context.ReservedContextPlugin;
import com.flybits.context.plugins.FlybitsContextPlugin;

import org.jetbrains.annotations.NotNull;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.Objects;

public class Application extends android.app.Application {
    static final String CHANNEL_ID = "com.flybits.concierge.channel.id";
    @Override
    public void onCreate() {
        super.onCreate();
        FlybitsManager.setLoggingVerbosity(VerbosityLevel.ALL);

    }

    //TODO start the context plugin
    void startPlugins() {
        // Start location plugin
        ContextManager.start(
                getApplicationContext(),
                new FlybitsContextPlugin.Builder(ReservedContextPlugin.GEOFENCE_LOCATION).build()
        );
        // Start battery plugin
//        ContextManager.start(
//                getApplicationContext(),
//                new FlybitsContextPlugin.Builder(ReservedContextPlugin.BATTERY).build()
//        );
    }
}
