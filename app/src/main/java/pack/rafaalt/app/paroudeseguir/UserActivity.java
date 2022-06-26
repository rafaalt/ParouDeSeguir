package pack.rafaalt.app.paroudeseguir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        TextView txt = findViewById(R.id.txtA);
        Intent intent = getIntent();
        ContaPrincipal conta = (ContaPrincipal) intent.getSerializableExtra("conta");
        conta.verificaNaoSegueVolta();
        txt.setText(conta.getSeguidores().size() + "-" + conta.getSeguindo().size() + "-" + conta.getNaoSegueVolta().size() + "\n");

        for(int i = 0; i<conta.getNaoSegueVolta().size(); i++){
            String texto = txt.getText().toString();
            txt.setText(texto + conta.getNaoSegueVolta().get(i) + "\n");
        }
    }
}