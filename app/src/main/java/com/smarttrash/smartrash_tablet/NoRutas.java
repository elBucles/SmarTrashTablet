package com.smarttrash.smartrash_tablet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoRutas extends AppCompatActivity {

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_rutas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey() != null){
                    ref.child(dataSnapshot.getKey()).removeValue();
                    showDialog();
                }
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override public void onCancelled(DatabaseError databaseError) { }
        });

    }

    public void showDialog(){
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(1000);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.findViewById(R.id.btnDirecciones).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sig = new Intent(NoRutas.this, Maps.class);
                startActivity(sig);
            }
        });
        dialog.show();
    }


}
