package pack.rafaalt.app.paroudeseguir;

import android.media.audiofx.Equalizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ContaPrincipal implements Serializable {
    private long id;
    private String username;
    private String nomeCompleto;
    private String iconUrl;
    private boolean isPrivate;
    private boolean isVerified;
    private String biography;
    private int qtSeguidores;
    private int qtSeguindo;
    private int qtMedia;
    private ArrayList<Conta> naoSegueVolta;
    private ArrayList<Conta> seguidores;
    private ArrayList<Conta> seguindo;

    public ContaPrincipal(long id, String username, String nomeCompleto, String iconUrl, boolean isPrivate,
                          boolean isVerified, String biography, int qtSeguidores, int qtSeguindo, int qtMedia) {
        this.id = id;
        this.username = username;
        this.nomeCompleto = nomeCompleto;
        this.iconUrl = iconUrl;
        this.isPrivate = isPrivate;
        this.isVerified = isVerified;
        this.biography = biography;
        this.qtSeguidores = qtSeguidores;
        this.qtSeguindo = qtSeguindo;
        this.qtMedia = qtMedia;
        this.seguidores = new ArrayList<>();
        this.seguindo = new ArrayList<>();
        this.naoSegueVolta = new ArrayList<>();
    }
    public Conta getUltimoSeguidor(){
        return this.seguidores.get(seguindo.size()-1);
    }
    public Conta getUltimoSeguindo(){
        return this.seguindo.get(seguindo.size()-1);
    }
    public void adicionarSeguidor(Conta conta){
        this.seguidores.add(conta);
    }

    public void adicionarSeguindo(Conta conta){
        this.seguindo.add(conta);
    }

    public void verificaNaoSegueVolta(){
        for(Conta x : this.seguindo){
            if (!this.seguidores.contains(x))
                this.naoSegueVolta.add(x);
        }
    }

    public ArrayList<Conta> getNaoSegueVolta() {
        return naoSegueVolta;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getBiography() {
        return biography;
    }

    public int getQtSeguidores() {
        return qtSeguidores;
    }

    public int getQtSeguindo() {
        return qtSeguindo;
    }

    public int getQtMedia() {
        return qtMedia;
    }

    public ArrayList<Conta> getSeguidores() {
        return seguidores;
    }

    public ArrayList<Conta> getSeguindo() {
        return seguindo;
    }

    public void setSeguidores(ArrayList<Conta> array){
        this.seguidores = array;
    }
}

