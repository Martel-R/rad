package com.martel.rad.models;

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
    public String p13x18;
    public String p18x24;
    public String p24x30;
    public String p30x34;
    public String p35x35;
    public String p35x43;
    public String idade;
    public String hora;
    public String dataa;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String paciente, String proc, String p13x18, String p18x24, String p24x30, String p30x34, String p35x35, String p35x43, String idade, String hora, String dataa) {
        this.uid = uid;
        this.author = author;
        this.paciente = paciente;
        this.proc = proc;
        this.p13x18 = p13x18;
        this.p18x24 = p18x24;
        this.p24x30 = p24x30;
        this.p30x34 = p30x34;
        this.p35x35 = p35x35;
        this.p35x43 = p35x43;
        this.idade = idade;
        this.hora = hora;
        this.dataa = dataa;
    }


    public Map<String, Boolean> getStars() {
        return stars;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("paciente", paciente);
        result.put("proc", proc);
        result.put("p13x18", p13x18);
        result.put("p18x24", p18x24);
        result.put("p24x30", p24x30);
        result.put("p30x40", p30x34);
        result.put("p35x35", p35x35);
        result.put("p35x43", p35x43);
        result.put("idade", idade);
        result.put("hora", hora);
        result.put("data", dataa);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
