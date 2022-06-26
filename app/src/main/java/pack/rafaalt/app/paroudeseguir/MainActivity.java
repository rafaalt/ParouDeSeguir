package pack.rafaalt.app.paroudeseguir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        try {
//            Thread.sleep(3000);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}