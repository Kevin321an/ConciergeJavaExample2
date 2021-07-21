package com.flybits.conciergejavaexample;

import com.flybits.commons.library.api.Region;
import com.flybits.commons.library.logging.VerbosityLevel;
import com.flybits.concierge.ConciergeConfiguration;
import com.flybits.concierge.FlybitsConcierge;
import com.flybits.concierge.FlybitsNavigator;
import com.flybits.concierge.models.ActionCard;
import com.flybits.concierge.viewactionhandlers.ActionCardUserActionHandler;
import com.flybits.concierge.viewproviders.ActionCardViewProvider;
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

        //TODO initialization
        FlybitsConcierge flybitsConcierge = FlybitsConcierge.with(this);
        flybitsConcierge.setLoggingVerbosity(VerbosityLevel.ALL);

        if (!flybitsConcierge.isInitialized()) {
            ConciergeConfiguration configuration =
//                    new ConciergeConfiguration.Builder("83D29279-13FD-4EDE-8732-9A271F390818")
//                    new ConciergeConfiguration.Builder("BF3F35E4-19A4-40DE-AE16-79B1918403F0")
//                    new ConciergeConfiguration.Builder("1299666E-9ED9-4617-AECE-52E964F0BB45")
                    new ConciergeConfiguration.Builder("C0C7D7D7-9716-4223-B4DB-1C9CC560E3C3")
//                            .setGatewayUrl(Region.EUROPE.getUrl())
//                            .setGatewayUrl("https://api.mc-us1.flybits.com")
                            .setGatewayUrl(Region.Demo.getUrl())
                            .build();
            flybitsConcierge.initialize(configuration);
        }


        ActionCardViewProvider actionCardViewProvider = new ActionCardViewProvider(getApplicationContext(),
                new ActionCardUserActionHandler() {
                    @Override
                    public void onUserAction(final int action, @NotNull final ActionCard data,
                            @NotNull final FlybitsNavigator flybitsNavigator) {
                        switch (action) {
                            case PRIMARY_CLICKED: {
                                if (data.getButtons() == null || data.getButtons().getList().size() < 1 ||
                                        data.getButtons().getList().get(0).getText() == null) {
                                    break;
                                }
                                switch ((Objects.requireNonNull(data.getButtons().getList().get(0).getText())
                                        .getValue())) {
                                    case "Test": {
                                        //Open the settings page of the app
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        startActivity(intent.setData(uri));
                                        break;
                                    }
                                    case "Test 2": {
                                        //Open the Android OS device settings
                                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                                        break;
                                    }
                                    //use the default action that is defined in ES
                                    default:
                                        super.onUserAction(action, data, flybitsNavigator);
                                }
                            }
                            case SECONDARY_CLICKED: {
                                if (data.getButtons() == null || data.getButtons().getList().size() < 2 ||
                                        data.getButtons().getList().get(1).getText() == null) {
                                    break;
                                }

                            switch ((Objects.requireNonNull(data.getButtons().getList().get(0).getText()).getValue())) {
                                case "Test 3": {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    startActivity(intent.setData(uri));
                                    break;
                                }
                                case "Test 4": {
                                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                                    break;
                                }
                                default:
                                    super.onUserAction(action, data, flybitsNavigator);
                            }
                        }
                        default:
                        super.onUserAction(action, data, flybitsNavigator);
                    }
                }
    });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Smart Rewards", importance);
            mChannel.setDescription("Notifications for smart rewards");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }


//        flybitsConcierge.registerFlybitsViewProvider(actionCardViewProvider);

//        val customizedActionCard = ActionCardViewProvider(applicationContext, object : ActionCardUserActionHandler()
//        {
//            override fun onUserAction(action: Int, data: ActionCard, flybitsNavigator: FlybitsNavigator)
//            {
//                //TODO implement the logic here
//                when (action) {
//                PRIMARY_CLICKED -> {
//                    when (data.buttons?.list?.get(0)?.text?.value) {
//                        "Test" -> {
//                            //Open the settings page of the app
//                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                            val uri = Uri.fromParts("package", packageName, null)
//                            intent.data = uri
//                            startActivity(intent)
//                        }
//                        "Test 2" -> {
//                            //Open the Android OS device settings
//                            startActivity(Intent(Settings.ACTION_SETTINGS))
//                        }
//                    else -> {
//                            //use the default action that is defined in ES
//                            super.onUserAction(action, data, flybitsNavigator)
//                        }
//                    }
//                }
//                //TODO implement the logic here
//                SECONDARY_CLICKED -> {
//                    when (data.buttons?.list?.get(1)?.text?.value) {
//                        "Test 3" -> {
//                            //Open the settings page of the app
//                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                            val uri = Uri.fromParts("package", packageName, null)
//                            intent.data = uri
//                            startActivity(intent)
//                        }
//                        "Test 4" -> {
//                            //Open the Android OS device settings
//                            startActivity(Intent(Settings.ACTION_SETTINGS))
//                        }
//                    else -> {
//                            //use the default action that is defined in ES
//                            super.onUserAction(action, data, flybitsNavigator)
//                        }
//                    }
//                }
//            else -> {
//                    //use the default action that is defined in ES
//                    super.onUserAction(action, data, flybitsNavigator)
//                }
//            }
//            }
//        })
//
//
//
    }

    //TODO start the context plugin
    void startPlugins() {
        // Start location plugin
        ContextManager.start(
                getApplicationContext(),
                new FlybitsContextPlugin.Builder(ReservedContextPlugin.LOCATION).build()
        );
        // Start battery plugin
        ContextManager.start(
                getApplicationContext(),
                new FlybitsContextPlugin.Builder(ReservedContextPlugin.BATTERY).build()
        );
    }
}
