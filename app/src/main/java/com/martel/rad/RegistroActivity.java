package com.martel.rad;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        final EditText etData = findViewById(R.id.et_data);
        final EditText etIdade = findViewById(R.id.et_idade);
        final EditText etHora = findViewById(R.id.et_hora);
        final EditText etQtd = findViewById(R.id.et_qtd);
        final TextInputEditText tietNome = findViewById(R.id.tiet_nome);
        final TextInputEditText tietProc = findViewById(R.id.tiet_proc);
        final TextInputEditText tietObs = findViewById(R.id.tiet_obs);
        Button btnSalvar = findViewById(R.id.btn_salvar);
        Button btnCancelar = findViewById(R.id.btn_cancelar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Paciente");

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ref = myRef.push().toString();
                myRef.child(ref).child("data").setValue(etData.getText().toString());
                myRef.child(ref).child("funcionario").setValue("");
                myRef.child(ref).child("hora_exame").setValue(etHora.getText().toString());
                myRef.child(ref).child("nome").setValue(tietNome.getText().toString());
                myRef.child(ref).child("idade").setValue(etIdade.getText().toString());
                myRef.child(ref).child("procedimento").setValue(tietProc.getText().toString());
                myRef.child(ref).child("qtd_peliculas").setValue(etQtd.getText().toString());
                myRef.child(ref).child("obs").setValue(tietObs.getText().toString());

                Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
