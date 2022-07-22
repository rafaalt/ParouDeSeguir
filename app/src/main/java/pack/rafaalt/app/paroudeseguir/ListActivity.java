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

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        ArrayList<Conta> contas = (ArrayList<Conta>) intent.getSerializableExtra("contas");
        int id = intent.getIntExtra("idAtual", R.id.bottomNav_Parou);
        ContaPrincipal conta = (ContaPrincipal) intent.getSerializableExtra("contaP");

        BottomNavigationView bottomNav = findViewById(R.id.userMenu);
        bottomNav.setSelectedItemId(id);

        //conta.verificaNaoSegueVolta();

        ListView lista = this.findViewById(R.id.listActivity);
        UserAdapter userAdapter = new UserAdapter(contas,this);
        lista.setAdapter(userAdapter);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNav_Perfil:
                        return true;

                    case R.id.bottomNav_NaoSegue:
                        Intent intent1 = new Intent(ListActivity.this, ListActivity.class);
                        intent1.putExtra("contas", conta.getNaoSegueVolta());
                        intent1.putExtra("idAtual", R.id.bottomNav_NaoSegue);
                        startActivity(intent1);
                        return true;

                    case R.id.bottomNav_Parou:
                        Intent intent2 = new Intent(ListActivity.this, ListActivity.class);
                        intent2.putExtra("contas", conta.getParouDeSeguir());
                        intent2.putExtra("idAtual", R.id.bottomNav_Parou);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });
    }
}