package com.smarttrash.smartrash_tablet;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        boolean b = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent =new Intent(Welcome.this, Maps.class);
                startActivity(intent);
                finish();

            }
        }, 2000);
    }
}
