package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.database.DatabaseReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tuhocandroid.navdrawerandtablayout.Object.Comment;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentSong extends AppCompatActivity {

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView textViewSongName;
    private Song song;
    private Button sendButton;
    private EditText messageEditText;
    private String userName;
    private String avartaImage;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Comment, MessageViewHolder> mFirebaseAdapter;
    private String Child_Comment;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferencesGetProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_song);
        Bundle bundleSong = getIntent().getBundleExtra("Song");

        song = (Song) bundleSong.getSerializable("Song");
        Child_Comment = "Comment/"+String.valueOf((int) song.getId());
        textViewSongName = (TextView) findViewById(R.id.textviewSongName);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.recycle_comment);
        sendButton = (Button) findViewById(R.id.sendButton);
        messageEditText = (EditText) findViewById(R.id.messageEditText);

        progressDialog = new ProgressDialog(CommentSong.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait a second");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.onStart();

        mLinearLayoutManager = new LinearLayoutManager(CommentSong.this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        textViewSongName.setText(song.getSongName());
        Typeface Alex_font = Typeface.createFromAsset(getAssets(),"fonts/AlexBrush-Regular.ttf");
        textViewSongName.setTypeface(Alex_font);

        sharedPreferencesGetProfile = getSharedPreferences(ShareFacebookActivity.PROFILE_USER, MODE_PRIVATE);
        userName = sharedPreferencesGetProfile.getString("name", "default");
        avartaImage = sharedPreferencesGetProfile.getString("avatar_url", ShareFacebookActivity.IMAGE_DEFAUL);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog.dismiss();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, MessageViewHolder>(
                Comment.class,
                R.layout.item_message,
                MessageViewHolder.class,
                mDatabaseReference.child(Child_Comment)
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Comment model, int position) {
                viewHolder.senderTextView.setText(model.getmPersonName());
                viewHolder.messageTextView.setText(model.getmComment());
                Picasso.with(CommentSong.this).load(model.getmUrlPhoto()).placeholder(R.drawable.ic_account_circle_black_36dp)
                        .error(R.drawable.ic_account_circle_black_36dp).into(viewHolder.messengerImageView);

            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int commentCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                        (positionStart >= (commentCount - 1) && lastVisiblePosition == (positionStart -1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0)
                    sendButton.setEnabled(true);
                else
                    sendButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment(userName, messageEditText.getText().toString(), avartaImage);
                mDatabaseReference.child(Child_Comment).push().setValue(comment);
                messageEditText.setText("");
            }
        });
    }
}

class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView messageTextView;
    CircleImageView messengerImageView;
    TextView senderTextView;

    public MessageViewHolder(View v) {
        super(v);
        messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
        senderTextView = (TextView) itemView.findViewById(R.id.senderTextView);
        messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
    }
}