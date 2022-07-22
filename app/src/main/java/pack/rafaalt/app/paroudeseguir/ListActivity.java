package pack.rafaalt.app.paroudeseguir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import pack.rafaalt.app.paroudeseguir.model.ContaPrincipal;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Intent intent = getIntent();
        ContaPrincipal conta = (ContaPrincipal) intent.getSerializableExtra("conta");
        //conta.verificaNaoSegueVolta();

        ListView lista = this.findViewById(R.id.listActivity);
        UserAdapter userAdapter = new UserAdapter(conta.getNaoSegueVolta(),this);
        lista.setAdapter(userAdapter);
    }
}