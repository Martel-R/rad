package com.martel.rad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    ArrayList<Registro> lista=new ArrayList<>();
    ArrayAdapter mAdapter;




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseListAdapter<Registro> fAdapter = new FirebaseListAdapter<Registro>(this, Registro.class, R.layout.single_row,databaseReference) {
            @Override
            protected void populateView(View v, Registro model, int position) {
                TextView tv_nome = findViewById(R.id.tv_nome);
                TextView tv_proc = findViewById(R.id.tv_proc);
                TextView tv_obs = findViewById(R.id.tv_obs);
                TextView tv_idade = findViewById(R.id.tv_idade);
                TextView tv_qtd = findViewById(R.id.tv_qtd);

                tv_nome.setText(model.getNome());
                tv_proc.setText(model.getProc());
                tv_obs.setText(model.getObs());
                tv_idade.setText(model.getIdade());
                tv_qtd.setText(model.getQtd());

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyAdapter adapter = new MyAdapter(lista, getApplicationContext());

        listView= (ListView) findViewById(R.id.lista);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i);
                finish();
            }
        });

        progressDialog=new ProgressDialog(this);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://unimedradiologia.firebaseio.com/Paciente");

        progressDialog.setMessage("Please Wait, Loading Data From Server");
        progressDialog.show();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                lista.add(dataSnapshot.getValue(Registro.class));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                lista.remove(dataSnapshot.getValue(Registro.class));

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
