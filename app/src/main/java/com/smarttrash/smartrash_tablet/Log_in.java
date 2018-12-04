package com.smarttrash.smartrash_tablet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Log_in extends Activity {

    ImageButton sig;
    DatabaseReference db;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sig = findViewById(R.id.btn_login);

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sig = new Intent(Log_in.this, NoRutas.class);
                startActivity(sig);
            }
        });

        db = FirebaseDatabase.getInstance().getReference().child("tarjetas");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Tarjeta tarjeta = dataSnapshot.getValue(Tarjeta.class);
                validateLogin(tarjeta);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override public void onCancelled(DatabaseError databaseError) { }
        };

        db.addChildEventListener(childEventListener);
    }

    @Override
    public void onBackPressed() {

    }

    public void validateLogin(Tarjeta tarjeta){
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);
        db.child(String.valueOf(tarjeta.id)).removeValue();
        if(tarjeta.tipo.trim().equals("conductor")){
            db.removeEventListener(childEventListener);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            Intent sig = new Intent(Log_in.this, NoRutas.class);
            startActivity(sig);
            finish();
        }

    }


}

