package com.smarttrash.smartrash_tablet;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Log_in extends Activity {

    ImageButton sig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sig = findViewById(R.id.btn_login);

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sig = new Intent(Log_in.this, Welcome.class);
                startActivity(sig);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}

