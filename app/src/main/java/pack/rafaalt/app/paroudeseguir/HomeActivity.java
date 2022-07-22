package pack.rafaalt.app.paroudeseguir;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pack.rafaalt.app.paroudeseguir.model.Conta;
import pack.rafaalt.app.paroudeseguir.model.ContaPrincipal;

//1698663381
public class HomeActivity extends AppCompatActivity {
    ContaPrincipal conta;
    int iteracoes = 0;
    int erros = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnExecutar = findViewById(R.id.btnExecute);
        EditText txtUsername = findViewById(R.id.txtUsername);

        btnExecutar.setOnClickListener(view -> {
            searchUser(txtUsername.getText().toString());
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
                                Toast.makeText(HomeActivity.this, "tente novamente", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
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
                                                TextView txtA = findViewById(R.id.home_txtA);
                                                TextView txtB = findViewById(R.id.home_txtB);
                                                txtA.setText(conta.getUsername() + ", id: " + conta.getId() + "\n");
                                                //txtB.setText("Iteracoes" + iteracoes + "\n");
                                                iteracoes++;
                                                getFollowers(id, "0");
                                                //getFollowerAux();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }

    public void getFollowers(Long id, String nextId) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        String url = "";
        if (nextId.equals("0"))
            url = "https://instagram-scraper-2022.p.rapidapi.com/ig/followers/?id_user=" + id;
        else
            url = "https://instagram-scraper-2022.p.rapidapi.com/ig/followers/?id_user=" + id + "&next_max_id=" + nextId;
        OkHttpClient cliente = new OkHttpClient();
        if (conta.contarSeguidores()) {
            getFollowing(id, "0");
        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("X-RapidAPI-Key", "2295348d0fmshd44c2731982934ap1fd835jsnabb4c3092fec")
                    .addHeader("X-RapidAPI-Host", "instagram-scraper-2022.p.rapidapi.com")
                    .build();
            cliente.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("WebService", "Erro Followers");
                    erros++;
                    if(erros < 6){
                        getFollowers(id, "0");
                    }
                    else
                    progressBar.setVisibility(View.INVISIBLE);
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
                                    String userdata = jsonObject.getString("users");
                                    Boolean bigList = jsonObject.getBoolean("big_list");
                                    JSONArray jsonArray = new JSONArray(userdata);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonUser = jsonArray.getJSONObject(i);
                                        Long idU = jsonUser.getLong("pk");
                                        String nomeCompleto = jsonUser.getString("full_name");
                                        String iconUrl = jsonUser.getString("profile_pic_url");
                                        String usernameRet = jsonUser.getString("username");
                                        boolean isPrivate = jsonUser.getBoolean("is_private");
                                        boolean isVerified = jsonUser.getBoolean("is_verified");
                                        Conta seguidor = new Conta(usernameRet, nomeCompleto, iconUrl, isPrivate, isVerified, idU);
                                        if(!conta.getSeguidores().contains(seguidor))
                                            conta.adicionarSeguidor(seguidor);
                                        }
                                    TextView txtA = findViewById(R.id.home_txtA);
                                    TextView txtB = findViewById(R.id.home_txtB);
                                    txtA.setText(conta.getSeguidores().size() + "/" + conta.getQtSeguidores() + "-" + conta.getSeguidoresAtivos() + "\n" );
                                    //txtB.setText("Seguidores: " + iteracoes + "\n");
                                    iteracoes++;
                                    String nextMaxId = "";
                                    if(bigList){
                                        nextMaxId = jsonObject.getString("next_max_id");
                                        getFollowers(id, nextMaxId);
                                        //getFollowerAux();
                                    }
                                    else{
                                        int segAtivos = Integer.parseInt(nextId) + jsonArray.length();
                                        conta.setSeguidoresAtivos(segAtivos);
                                        getFollowers(id, "0");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void getFollowing(Long id, String nextId) {
        //conta.setSeguindo(conta.getSeguidores());
        ProgressBar progressBar = findViewById(R.id.progressBar);
        String url = "";
        if (nextId.equals("0"))
            url = "https://instagram-scraper-2022.p.rapidapi.com/ig/following/?id_user=" + id;
        else
            url = "https://instagram-scraper-2022.p.rapidapi.com/ig/following/?id_user=" + id + "&next_max_id=" + nextId;
        OkHttpClient cliente = new OkHttpClient();
        if (conta.contarSeguindo()) {
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(HomeActivity.this, UserActivity.class);
            intent.putExtra("conta", conta);
            startActivity(intent);
        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("X-RapidAPI-Key", "2295348d0fmshd44c2731982934ap1fd835jsnabb4c3092fec")
                    .addHeader("X-RapidAPI-Host", "instagram-scraper-2022.p.rapidapi.com")
                    .build();
            cliente.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("WebService", "Erro Following");
                    erros++;
                    if(erros < 8){
                        getFollowing(id, "0");
                    }
                    else
                        progressBar.setVisibility(View.INVISIBLE);
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
                                    String userdata = jsonObject.getString("users");
                                    Boolean bigList = jsonObject.getBoolean("big_list");
                                    JSONArray jsonArray = new JSONArray(userdata);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonUser = jsonArray.getJSONObject(i);
                                        Long idU = jsonUser.getLong("pk");
                                        String nomeCompleto = jsonUser.getString("full_name");
                                        String iconUrl = jsonUser.getString("profile_pic_url");
                                        String usernameRet = jsonUser.getString("username");
                                        boolean isPrivate = jsonUser.getBoolean("is_private");
                                        boolean isVerified = jsonUser.getBoolean("is_verified");
                                        Conta c = new Conta(usernameRet, nomeCompleto, iconUrl, isPrivate, isVerified, idU);
                                        if(!conta.getSeguindo().contains(c))
                                            conta.adicionarSeguindo(c);
                                       }
                                    TextView txtA = findViewById(R.id.home_txtA);
                                    TextView txtB = findViewById(R.id.home_txtB);
                                    txtA.setText(conta.getSeguindo().size() + "/" + conta.getQtSeguindo() + "-" + conta.getSeguindoAtivos() + "\n");
                                    txtB.setText("Seguindo: " + iteracoes + "\n");
                                    iteracoes++;
                                    String nextMaxId = "";
                                    if(bigList){
                                        nextMaxId = jsonObject.getString("next_max_id");
                                        getFollowing(id, nextMaxId);
                                        //getFollowingAux();
                                    }
                                    else{
                                        int segAtivos = Integer.parseInt(nextId) + jsonArray.length();
                                        conta.setSeguindoAtivos(segAtivos);
                                        getFollowing(id, "0");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void getFollowingAux(){
        ProgressBar progressBar = findViewById(R.id.progressBar);
        for(int i = 0; i<conta.getQtSeguindo(); i++){
            Random random = new Random();
            Conta c = new Conta("user" + i*2, "Rafael" + i, "", true, false, random.nextInt(250));
            conta.adicionarSeguindo(c);
        }
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(HomeActivity.this, UserActivity.class);
        intent.putExtra("conta", conta);
        startActivity(intent);
    }

    public void getFollowerAux(){
        for(int i = 0; i<conta.getQtSeguidores(); i++){
            Random random = new Random();
            Conta c = new Conta("user" + i*2, "Rafael" + i, "", true, false, random.nextInt(250));
            conta.adicionarSeguidor(c);
        }
        getFollowingAux();
    }
}