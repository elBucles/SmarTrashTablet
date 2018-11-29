package com.smarttrash.smartrash_tablet;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Bundle;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        boolean b = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent sig =new Intent(Welcome.this, Maps.class);
                startActivity(sig);
                finish();

            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {

    }

}
