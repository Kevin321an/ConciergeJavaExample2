package com.flybits.conciergejavaexample;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.flybits.android.push.FlyNotification;
import com.flybits.android.push.PushManager;
import com.flybits.android.push.models.newPush.DisplayablePush;
import com.flybits.android.push.receiver.NotificationClickedReceiver;
import com.flybits.android.push.services.PushService;
import com.flybits.commons.library.api.results.callbacks.ObjectResultCallback;
import com.flybits.commons.library.exceptions.FlybitsException;

import org.jetbrains.annotations.NotNull;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static com.flybits.conciergejavaexample.Application.CHANNEL_ID;

public class AppDirectPush extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Context act = this;

        PushManager.parseDisplayablePushNotification(
                remoteMessage.getData(), new ObjectResultCallback<DisplayablePush>() {
                    @Override
                    public void onSuccess(final DisplayablePush displayablePush) {
                        new FlyNotification.Builder(
                                act,
                                displayablePush,
                                CHANNEL_ID,
                                R.mipmap.ic_flybits_notification
                        )
                                .setPendingIntent(onNotificationClick(displayablePush))//this is notification click
                                // to action
                                .build()
                                .show();
                    }

                    @Override
                    public void onException(@NotNull final FlybitsException e) {

                    }
                }
        );

    }

    private PendingIntent onNotificationClick(DisplayablePush push) {
        final String EXTRA_PUSH_NOTIFICATION = "com.flybits.android.push.services.push_notification"; // Please
        // define this instance somewhere else  and your launcher class will need this as well
        Intent intent =
                new Intent(this, NotificationClickedReceiver.class).putExtra(EXTRA_PUSH_NOTIFICATION,
                        push);
        return PendingIntent.getBroadcast(
                this, push.getId().hashCode(), intent, PendingIntent.FLAG_CANCEL_CURRENT
        );
    }
}
