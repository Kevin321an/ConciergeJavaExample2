package com.flybits.conciergejavaexample;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.flybits.android.push.models.newPush.DisplayablePush;
import com.flybits.commons.library.api.idps.AnonymousIDP;
import com.flybits.commons.library.exceptions.FlybitsException;
import com.flybits.concierge.AuthenticationStatusListener;
import com.flybits.concierge.ConciergeFragment;
import com.flybits.concierge.DisplayConfiguration;
import com.flybits.concierge.FlybitsConcierge;
import com.flybits.concierge.enums.ShowMode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    FlybitsConcierge concierge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        concierge = FlybitsConcierge.with(this);

        grantPermission(this);

        //TODO Configuration
//        concierge.authenticate(new AnonymousIDP());

        DisplayConfiguration displayConfiguration = new DisplayConfiguration(
                ConciergeFragment.MenuType.MENU_TYPE_APP_BAR,
                ShowMode.NEW_ACTIVITY, true
        );

//        DisplayConfiguration displayConfiguration = new DisplayConfiguration(
//                ConciergeFragment.MenuType.MENU_TYPE_TAB,
//                ShowMode.OVERLAY, false
//        );

        concierge.isAuthenticated();
        concierge.authenticate(new AnonymousIDP());
//        FlybitsManager.isConnected(this, true, this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Show the content
                if (concierge.isAuthenticated()) {
                    concierge.show(displayConfiguration);
                    //start the context plugin
                    ((Application) getApplication()).startPlugins();
                } else {
                    concierge.authenticate(new AnonymousIDP());
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        handleFlybitPushIntent(getIntent());

        concierge.registerAuthenticationStateListener(new AuthenticationStatusListener() {
            @Override
            public void onAuthenticated() {
                Log.e("flybits", "onAuthenticated");
            }

            @Override
            public void onAuthenticationStarted() {
                Log.e("flybits", "onAuthenticationStarted");
            }

            @Override
            public void onAuthenticationError(final FlybitsException e) {
                Log.e("flybits", "onAuthenticationError");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void grantPermission(Activity activity) {
        ActivityCompat.requestPermissions(
                activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 123
        );
    }

    final String EXTRA_PUSH_NOTIFICATION = "com.flybits.android.push.services.push_notification";

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        handleFlybitPushIntent(intent);
    }

    private void handleFlybitPushIntent(Intent intent) {
        if (intent.hasExtra(EXTRA_PUSH_NOTIFICATION)) {
            DisplayablePush displayablePush = intent.getParcelableExtra(EXTRA_PUSH_NOTIFICATION);
            DisplayConfiguration displayConfiguration = new DisplayConfiguration(
                    ConciergeFragment.MenuType.MENU_TYPE_APP_BAR,
                    ShowMode.NEW_ACTIVITY,
                    true
            );
            concierge.showPush(displayConfiguration, displayablePush);
        }
    }
}