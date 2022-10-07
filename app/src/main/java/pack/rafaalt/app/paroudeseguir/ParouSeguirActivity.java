package pack.rafaalt.app.paroudeseguir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import pack.rafaalt.app.paroudeseguir.model.Conta;
import pack.rafaalt.app.paroudeseguir.model.ContaPrincipal;

public class ParouSeguirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parou_seguir);

        Intent intent = getIntent();
        ContaPrincipal conta = (ContaPrincipal) intent.getSerializableExtra("conta");
        int id = intent.getIntExtra("idAtual", R.id.bottomNav_Parou);

        BottomNavigationView bottomNav = findViewById(R.id.userMenu2);
        bottomNav.setSelectedItemId(id);

        //conta.verificaNaoSegueVolta();

        ListView lista = this.findViewById(R.id.listActivity2);
        UserAdapter userAdapter = new UserAdapter(conta.getParouDeSeguir(),this);
        lista.setAdapter(userAdapter);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNav_Perfil:
                        Intent intent1 = new Intent(ParouSeguirActivity.this, UserActivity.class);
                        intent1.putExtra("conta", conta);
                        intent1.putExtra("idAtual", R.id.bottomNav_Perfil);
                        startActivity(intent1);
                        return true;

                    case R.id.bottomNav_NaoSegue:
                        Intent intent2 = new Intent(ParouSeguirActivity.this, NaoSegueActivity.class);
                        intent2.putExtra("conta", conta);
                        intent2.putExtra("idAtual", R.id.bottomNav_Parou);
                        startActivity(intent2);
                        return true;

                    case R.id.bottomNav_Parou:
                        return true;
                }
                return false;
            }
        });
    }
}