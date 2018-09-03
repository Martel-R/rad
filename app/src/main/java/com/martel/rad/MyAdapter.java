package com.martel.rad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<Registro> list = new ArrayList<>();
    Context context;

    public MyAdapter(ArrayList<Registro> lista, Context context) {
        this.list = lista;
        this.context = context;
    }

    @Override

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v =inflater.inflate(R.layout.single_row,viewGroup,false);

        TextView tv_name= (TextView) v.findViewById(R.id.tv_nome);
        TextView tv_proc= (TextView) v.findViewById(R.id.tv_proc);
        TextView tv_obs= (TextView) v.findViewById(R.id.tv_obs);
        TextView tv_idade= (TextView) v.findViewById(R.id.tv_idade);
        TextView tv_qtd= (TextView) v.findViewById(R.id.tv_qtd);

        Registro registro = list.get(i);

        tv_name.setText(registro.getNome());
        tv_idade.setText(registro.getIdade());
        tv_obs.setText(registro.getObs());
        tv_proc.setText(registro.getProc());
        tv_qtd.setText(registro.getQtd());

        return view;
    }
}
