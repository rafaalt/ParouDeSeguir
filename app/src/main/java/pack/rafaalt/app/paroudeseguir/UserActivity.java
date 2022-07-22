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

import pack.rafaalt.app.paroudeseguir.db.ParouDAO;
import pack.rafaalt.app.paroudeseguir.db.ParouDB;
import pack.rafaalt.app.paroudeseguir.db.SeguidoresDAO;
import pack.rafaalt.app.paroudeseguir.db.SeguidoresDB;
import pack.rafaalt.app.paroudeseguir.model.ContaPrincipal;
import pack.rafaalt.app.paroudeseguir.model.ImageDownloader;

public class UserActivity extends AppCompatActivity {
    ContaPrincipal conta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SeguidoresDB seguidoresDB = new SeguidoresDB(this);
        ParouDB parouDB = new ParouDB(this);
        SeguidoresDAO seguidoresDAO = new SeguidoresDAO(seguidoresDB);
        ParouDAO parouDAO = new ParouDAO(parouDB);

        Intent intent = getIntent();
        conta = (ContaPrincipal) intent.getSerializableExtra("conta");

        //Banco de Dados
        seguidoresDAO.criarTabela(conta.getUsername() + "");
        parouDAO.criarTabela(conta.getUsername() + "");
        conta.setSeguidoresAntigos(seguidoresDAO.getSeguidores(conta.getUsername() + ""));
        conta.setParouDeSeguir(parouDAO.getSeguidores(conta.getUsername() + ""));
        seguidoresDAO.deleteTable(conta.getUsername() + "");
        seguidoresDAO.preencherTabela(conta.getUsername() + "", conta.getSeguidores());
        conta.fazerVerificacoes();
        parouDAO.deleteTable(conta.getUsername() + "");
        parouDAO.preencherTabela(conta.getUsername() + "", conta.getParouDeSeguir());

            TextView txtUser = findViewById(R.id.user_txtUsername);
            TextView txtNome = findViewById(R.id.user_txtNomeCompleto);
            TextView followers1 = findViewById(R.id.user_followers1);
            TextView following1 = findViewById(R.id.user_following1);
            Button btnNao = findViewById(R.id.user_btnNao);


            carregarFoto();
            txtUser.setText("@" + conta.getUsername());
            txtNome.setText(conta.getNomeCompleto());
            followers1.setText(conta.getSeguidoresAtivos() + "");
            following1.setText(conta.getSeguindoAtivos() + "");

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