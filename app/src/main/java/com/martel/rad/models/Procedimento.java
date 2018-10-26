package com.martel.rad.models;

import java.util.HashMap;
import java.util.Map;

public class Procedimento {

    public String key;
    public String valor;

    public Procedimento() {
    }

    public Procedimento(String key, String valor) {
        this.key = key;
        this.valor = valor;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(key, valor);
        return result;
    }
}
