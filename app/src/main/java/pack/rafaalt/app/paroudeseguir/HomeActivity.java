package pack.rafaalt.app.paroudeseguir;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeActivity extends AppCompatActivity {
    ContaPrincipal conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnExecutar = findViewById(R.id.btnExecute);
        Button btnExecutar2 = findViewById(R.id.btnExecute2);
        EditText txtUsername = findViewById(R.id.txtUsername);

        btnExecutar.setOnClickListener(view -> {
            //searchUser(txtUsername.getText().toString());
            //txtUsername.setText(viewModel.getConta().getNomeCompleto());
        });

    }

    public void searchUser(String username) {
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                        String url = "https://instagram-scraper-2022.p.rapidapi.com/ig/info_username/?user=" + username;
                        OkHttpClient cliente = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .get()
                                .addHeader("X-RapidAPI-Key", "2295348d0fmshd44c2731982934ap1fd835jsnabb4c3092fec")
                                .addHeader("X-RapidAPI-Host", "instagram-scraper-2022.p.rapidapi.com")
                                .build();
                        cliente.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("WebService", "Erro SearchUser");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                if (response.isSuccessful()) {
                                    String resp = response.body().string();
                                    HomeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(resp);
                                                String userData = jsonObject.getString("user");
                                                JSONObject user = new JSONObject(userData);
                                                Long id = user.getLong("pk");
                                                String nomeCompleto = user.getString("full_name");
                                                String iconUrl = user.getString("profile_pic_url");
                                                String usernameRet = user.getString("username");
                                                boolean isPrivate = user.getBoolean("is_private");
                                                boolean isVerified = user.getBoolean("is_verified");
                                                String biography = user.getString("biography");
                                                int qtSeguidores = user.getInt("follower_count");
                                                int qtSeguindo = user.getInt("following_count");
                                                int qtMedia = user.getInt("media_count");
                                                conta = new ContaPrincipal(id, usernameRet, nomeCompleto, iconUrl, isPrivate, isVerified, biography, qtSeguidores, qtSeguindo, qtMedia);
                                                TextView txt = findViewById(R.id.txt);
                                                txt.setText(conta.getNomeCompleto() + ", id: " + conta.getId() + "\n");
                                                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                                                intent.putExtra("conta", conta);
                                                startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
}