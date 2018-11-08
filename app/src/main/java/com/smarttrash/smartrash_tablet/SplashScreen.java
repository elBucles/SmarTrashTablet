package com.smarttrash.smartrash_tablet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        boolean a = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent =new Intent(SplashScreen.this, Log_in.class);
                startActivity(intent);
                finish();

            }
        }, 2000);

        // Example of a call to a native method
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
