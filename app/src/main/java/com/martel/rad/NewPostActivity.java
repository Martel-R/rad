package com.martel.rad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.martel.rad.models.Post;
import com.martel.rad.models.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private AlertDialog peliculaDialog;
    // [END declare_database_ref]

    private EditText mNomeField;
    private Button btnProc;
    private Button btnListaProc;
    private TextView tvProc;
    private TextView tv13x18;
    private TextView tv18x24;
    private TextView tv24x30;
    private TextView tv30x40;
    private TextView tv35x35;
    private TextView tv35x43;
    private Button btnPeliculas;
    private EditText mIdadeField;
    private EditText mDataField;
    private EditText mHoraField;
    private String ss13x18;
    private String ss18x24;
    private String ss24x30;
    private String ss30x40;
    private String ss35x35;
    private String ss35x43;
     int count1;
     int count2;
     int count3;
     int count4;
     int count5;
     int count6;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mNomeField = findViewById(R.id.field_nome);
        btnProc = findViewById(R.id.btn_proc);
        btnListaProc = findViewById(R.id.btn_listaproc);
        mIdadeField = findViewById(R.id.field_idade);
        btnPeliculas = findViewById(R.id.btn_peliculas);
        findViewById(R.id.iv_mais1);
        findViewById(R.id.iv_menos1);
        findViewById(R.id.iv_mais2);
        findViewById(R.id.iv_menos2);
        findViewById(R.id.iv_mais3);
        findViewById(R.id.iv_menos3);
        findViewById(R.id.iv_mais4);
        findViewById(R.id.iv_menos4);
        findViewById(R.id.iv_mais5);
        findViewById(R.id.iv_menos5);
        findViewById(R.id.iv_mais6);
        findViewById(R.id.iv_menos6);

        mDataField = findViewById(R.id.field_data);
        mHoraField = findViewById(R.id.field_hora);
        mSubmitButton = findViewById(R.id.fab_submit_post);
        tv13x18 = findViewById(R.id.tx_13x18);
        tv18x24 = findViewById(R.id.tx_18x24);
        tv24x30 = findViewById(R.id.tx_24x30);
        tv30x40 = findViewById(R.id.tx_30x40);
        tv35x35 = findViewById(R.id.tx_35x35);
        tv35x43 = findViewById(R.id.tx_35x43);
        ss13x18 = (String) tv13x18.getText();
        ss18x24 = tv18x24.getText().toString();
        ss24x30 = tv24x30.getText().toString();
        ss30x40 = tv30x40.getText().toString();
        ss35x35 = tv35x35.getText().toString();
        ss35x43 = tv35x43.getText().toString();
        btnPeliculas.setOnClickListener(this);
        btnProc.setOnClickListener(this);
        btnListaProc.setOnClickListener(this);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String nome = mNomeField.getText().toString();
        final String proc = tvProc.getText().toString();
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
            tvProc.setError(REQUIRED);
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
                        } {
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
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } {
            mSubmitButton.setVisibility(View.INVISIBLE);
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
        int i = view.getId();

        count1 = Integer.parseInt(ss13x18);
        count2 = Integer.parseInt(ss18x24);
        count3 = Integer.parseInt(ss24x30);
        count4 = Integer.parseInt(ss30x40);
        count5 = Integer.parseInt(ss35x35);
        count6 = Integer.parseInt(ss35x43);
        if (i == R.id.iv_mais1) {
//            int a = Integer.parseInt(ss13x18);
             ++count1;
            atualizaContador(count1, tv13x18);
        } if (i == R.id.iv_menos1) {
            // int a = Integer.parseInt(ss13x18);
            --count1;
            atualizaContador(count1, tv13x18);
        } if (i == R.id.iv_mais2) {//18x24
//            // int a = Integer.parseInt(ss18x24);
             ++count2;
            atualizaContador(count2, tv18x24);

        } if (i == R.id.iv_menos2) {
//            // int a = Integer.parseInt(ss18x24);
            --count2;
            atualizaContador(count2, tv18x24);

        } if (i == R.id.iv_mais3) {
//            // int a = Integer.parseInt(ss24x30);
             ++count3;
            atualizaContador(count3,tv24x30);

        } if (i == R.id.iv_menos3) {
//            // int a = Integer.parseInt(ss24x30);
            --count3;
            atualizaContador(count3, tv24x30);

        } if (i == R.id.iv_mais4) {
//            // int a = Integer.parseInt(ss30x40);
             ++count4;
            atualizaContador(count4, tv30x40);

        } if (i == R.id.iv_menos4) {
            // int a = Integer.parseInt(ss30x40);
            --count4;
            atualizaContador(count4,tv30x40);

        } if (i == R.id.iv_mais5) {
            // int a = Integer.parseInt(ss35x35);
             ++count5;
            atualizaContador(count5, tv35x35);

        } if (i == R.id.iv_menos5) {
            // int a = Integer.parseInt(ss35x35);
            --count5;
            atualizaContador(count5, tv35x35);

        } if (i == R.id.iv_mais6) {

            // int a = Integer.parseInt(ss35x43);
             ++count6;
            atualizaContador(count6, tv35x43);
        } if (i == R.id.iv_menos6) {
            // int a = Integer.parseInt(ss35x43);
            --count6;
            atualizaContador(count6, tv35x43);

        } if (i == R.id.btn_peliculas) {
            startActivity(new Intent(NewPostActivity.this, ProcedimentoActivity.class));

        }

    }

    private void exemplo_layout() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout peliculaDialog.xml na view
        View view = li.inflate(R.layout.dialog_pelicula, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Películas");
        //definimos para o botão do layout um clickListener
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                InputStreamReader ir = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(ir);

            }
        });
        builder.setView(view);
        peliculaDialog = builder.create();
        peliculaDialog.show();
    }

    private void atualizaContador(int a, TextView tv) {
        tv.setText(a);
    }
    // [END write_fan_out]
}
