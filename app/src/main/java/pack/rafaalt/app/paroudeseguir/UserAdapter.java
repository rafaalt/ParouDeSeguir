package pack.rafaalt.app.paroudeseguir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import pack.rafaalt.app.paroudeseguir.model.Conta;
import pack.rafaalt.app.paroudeseguir.model.ImageDownloader;

public class UserAdapter extends BaseAdapter {

    private ArrayList<Conta> contas;
    private Context context;

    public UserAdapter(ArrayList<Conta> contas, Context context){
        this.contas = contas;
        this.context = context;
    }

    public UserAdapter(){}

    @Override
    public int getCount() {
        return this.contas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.contas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.contas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Conta contaAtual = this.contas.get(i);

        View retorno = LayoutInflater.from(this.context).inflate(R.layout.adapter_user, viewGroup, false);
        TextView username = retorno.findViewById(R.id.adapterUsername);
        TextView nomeCompleto = retorno.findViewById(R.id.adapterNomeCompleto);
        //ImageView fotoPerfil = retorno.findViewById(R.id.adadpterImagem);
        username.setText(contaAtual.getUsername());
        nomeCompleto.setText(contaAtual.getNomeCompleto());
        ProgressBar barraProgresso = retorno.findViewById(R.id.adapterProgressBar);
//        barraProgresso.setVisibility(View.VISIBLE);
//        Bitmap img = null;
//        img = downloadImage(contaAtual.getIconUrl());
//        fotoPerfil.setImageBitmap(img);
//        barraProgresso.setVisibility(View.INVISIBLE);
        return retorno;
    }

    private Bitmap downloadImage(String imgLink) {
        try {
            return ImageDownloader.download(imgLink);
        } catch (IOException e) {
            Log.e("UserAdapter", e.toString());
            return null;
        }
    }

    }