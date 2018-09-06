package com.google.firebase.quickstart.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;
    public String paciente;
    public String proc;
    public String qtd;
    public String idade;
    public String hora;
    public String dataa;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String nome, String proc, String qtd, String idade, String hora, String data) {
        this.uid = uid;
        this.author = author;
        this.paciente = nome;
        this.proc = proc;
        this.qtd = qtd;
        this.idade = idade;
        this.hora = hora;
        this.dataa = data;
    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getPaciente() {
        return paciente;
    }

    public String getProc() {
        return proc;
    }

    public String getQtd() {
        return qtd;
    }

    public String getIdade() {
        return idade;
    }

    public String getHora() {
        return hora;
    }

    public String getDataa() {
        return dataa;
    }

    public int getStarCount() {
        return starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("Funcionario", author);
        result.put("Paciente", paciente);
        result.put("Procedimento", proc);
        result.put("Peliculas", qtd);
        result.put("Idade", idade);
        result.put("Hora", hora);
        result.put("Data", dataa);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
