package pack.rafaalt.app.paroudeseguir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import pack.rafaalt.app.paroudeseguir.model.ContaPrincipal;
import pack.rafaalt.app.paroudeseguir.model.ImageDownloader;

public class UserActivity extends AppCompatActivity {
    ContaPrincipal conta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView txtUser = findViewById(R.id.user_txtUsername);
        TextView txtNome = findViewById(R.id.user_txtNomeCompleto);
        TextView followers1 = findViewById(R.id.user_followers1);
        TextView following1 = findViewById(R.id.user_following1);
        Button btnNao = findViewById(R.id.user_btnNao);
        Intent intent = getIntent();
        conta = (ContaPrincipal) intent.getSerializableExtra("conta");
        carregarFoto();
        txtUser.setText("@" + conta.getUsername());
        txtNome.setText(conta.getNomeCompleto());
        followers1.setText(conta.getQtSeguidores() + "");
        following1.setText(conta.getQtSeguindo() + "");

        btnNao.setOnClickListener(view -> {
            Intent intent1 = new Intent(UserActivity.this, ListActivity.class);
            intent1.putExtra("conta", conta);
            startActivity(intent1);
        });
    }

    private Bitmap downloadImage(String imgLink) {
        try {
            return ImageDownloader.download(imgLink);
        } catch (IOException e) {
            Log.e("UserActivity", e.toString());
            return null;
        }
    }

    private void carregarFoto(){
        ImageView fotoPerfil = findViewById(R.id.imagem);
        Thread threadSec = new Thread(){
            ProgressBar barraProgresso = findViewById(R.id.user_progressBar);
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        barraProgresso.setVisibility(View.INVISIBLE);
                    }
                });
                Bitmap img = UserActivity.this.downloadImage(conta.getIconUrl());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fotoPerfil.setImageBitmap(img);
                        barraProgresso.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        threadSec.start();
    }
}