package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tuhocandroid.navdrawerandtablayout.Object.Singer;
import com.tuhocandroid.navdrawerandtablayout.R;

public class SingerActivity extends AppCompatActivity {

    private TextView textViewSingerName;
    private TextView textViewSingerInfo;
    private ImageView imageViewSinger;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);

        Bundle bundleSinger = getIntent().getBundleExtra("Singer");
        Singer singer = (Singer) bundleSinger.getSerializable("Singer");

        textViewSingerInfo = (TextView) findViewById(R.id.textViewInfoSinger);
        textViewSingerName = (TextView) findViewById(R.id.textviewSingerName);
        imageViewSinger = (ImageView) findViewById(R.id.imageViewSinger);

        Picasso.with(SingerActivity.this).load(singer.getUrlPhoto()).error(R.drawable.defaultimage)
                .placeholder(R.drawable.defaultimage)
                .into(imageViewSinger);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Bold.otf");
        textViewSingerName.setText(singer.getSingerName());
        textViewSingerName.setTypeface(typeface);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("InforSinger/" + String.valueOf( (int) singer.getId())).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String singerInfo = (String) dataSnapshot.getValue();
                textViewSingerInfo.setText(singerInfo);
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
                textViewSingerInfo.setTypeface(typeface);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
