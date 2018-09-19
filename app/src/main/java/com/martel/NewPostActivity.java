package com.martel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martel.models.Post;
import com.martel.models.User;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mNomeField;
    private EditText mProcField;
    private ImageView p13x18i;
    private ImageView p13x18v;
    private ImageView p18x24i;
    private ImageView p18x24v;
    private ImageView p24x30i;
    private ImageView p24x30v;
    private ImageView p30x40i;
    private ImageView p30x40v;
    private ImageView p35x35i;
    private ImageView p35x35v;
    private ImageView p35x43i;
    private ImageView p35x43v;
    private TextView tv13x18;
    private TextView tv18x24;
    private TextView tv24x30;
    private TextView tv30x40;
    private TextView tv35x35;
    private TextView tv35x43;
    private EditText mIdadeField;
    private EditText mDataField;
    private EditText mHoraField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mNomeField = findViewById(R.id.field_nome);
        mProcField = findViewById(R.id.field_proc);
        mIdadeField = findViewById(R.id.field_idade);
        p13x18i = findViewById(R.id.iv_mais1);
        p13x18v = findViewById(R.id.iv_menos1);
        p18x24i = findViewById(R.id.iv_mais2);
        p18x24v = findViewById(R.id.iv_menos2);
        p24x30i = findViewById(R.id.iv_mais3);
        p24x30v = findViewById(R.id.iv_menos3);
        p30x40i = findViewById(R.id.iv_mais4);
        p30x40v = findViewById(R.id.iv_menos4);
        p35x35i = findViewById(R.id.iv_mais5);
        p35x35v = findViewById(R.id.iv_menos5);
        p35x43i = findViewById(R.id.iv_mais6);
        p35x43v = findViewById(R.id.iv_menos6);
        mDataField = findViewById(R.id.field_data);
        mHoraField = findViewById(R.id.field_hora);
        mSubmitButton = findViewById(R.id.fab_submit_post);

        p13x18i.setOnClickListener(this);
        p13x18v.setOnClickListener(this);
        p18x24v.setOnClickListener(this);
        p18x24v.setOnClickListener(this);
        p24x30v.setOnClickListener(this);
        p24x30v.setOnClickListener(this);
        p30x40v.setOnClickListener(this);
        p30x40v.setOnClickListener(this);
        p35x35v.setOnClickListener(this);
        p35x35v.setOnClickListener(this);
        p35x43v.setOnClickListener(this);
        p35x43v.setOnClickListener(this);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String nome = mNomeField.getText().toString();
        final String proc = mProcField.getText().toString();
        final String idade = mIdadeField.getText().toString();
        final String s13x18 = tv13x18.getText().toString();
        final String s18x24 = tv18x24.getText().toString();
        final String s24x30 = tv24x30.getText().toString();
        final String s30x40 = tv30x40.getText().toString();
        final String s35x35 = tv35x35.getText().toString();
        final String s35x43 = tv35x43.getText().toString();
        final String data = mDataField.getText().toString();
        final String hora = mHoraField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(nome)) {
            mNomeField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(proc)) {
            mProcField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(idade)) {
            mIdadeField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(data)) {
            mDataField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(hora)) {
            mHoraField.setError(REQUIRED);
            return;
        }


        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Salvando...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, nome, proc,s13x18,  s18x24,  s24x30,  s30x40,  s35x35,  s35x43,  idade, hora, data);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mNomeField.setEnabled(enabled);
        mProcField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String uid, String author, String paciente, String proc, String p13x18, String p18x24, String p24x30, String p30x34, String p35x35, String p35x43, String idade, String hora, String dataa){
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post( uid,  author,  paciente,  proc,  p13x18,  p18x24,  p24x30,  p30x34,  p35x35,  p35x43,  idade, hora, dataa);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + uid + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    @Override
    public void onClick(View view) {

    }
    // [END write_fan_out]
}
