package pack.rafaalt.app.paroudeseguir.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ContaPrincipal implements Serializable {
    private long id;
    private String username;
    private String nomeCompleto;
    private String iconUrl;
    private boolean isPrivate;
    private boolean isVerified;
    private String biography;
    private int qtSeguidores;
    private int seguidoresAtivos;
    private int seguindoAtivos;
    private int qtSeguindo;
    private int qtMedia;
    private ArrayList<Conta> naoSegueVolta;
    private ArrayList<Conta> seguidoresAntigos;
    private ArrayList<Conta> parouDeSeguir;
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
        this.seguidoresAtivos = qtSeguidores;
        this.seguindoAtivos = qtSeguindo;
        this.seguidores = new ArrayList<>();
        this.seguindo = new ArrayList<>();
        this.naoSegueVolta = new ArrayList<>();
        this.seguidoresAntigos = new ArrayList<>();
        this.parouDeSeguir = new ArrayList<>();
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
            if (!this.seguidores.contains(x)) {
                if (!this.naoSegueVolta.contains(x))
                    this.naoSegueVolta.add(x);
            }
        }
    }

    public void fazerVerificacoes(){
        verificaNaoSegueVolta();
        for(Conta x : this.seguidoresAntigos){
            if(!this.seguidores.contains(x)) {
                if (!this.parouDeSeguir.contains(x))
                    this.parouDeSeguir.add(x);
            }
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

    public boolean contarSeguidores(){
        if(seguidoresAtivos <= seguidores.size())
            return true;
        return false;
    }

    public boolean contarSeguindo(){
        if(seguindoAtivos <= seguindo.size())
            return true;
        return false;
    }

    public void setSeguidoresAtivos(int valor){
        this.seguidoresAtivos = valor;
    }

    public void setSeguindoAtivos(int valor){
        this.seguindoAtivos = valor;
    }

    public int getSeguidoresAtivos() {
        return seguidoresAtivos;
    }

    public int getSeguindoAtivos() {
        return seguindoAtivos;
    }

    public void setSeguindo(ArrayList<Conta> seguindo) {
        this.seguindo = seguindo;
    }

    public void setNaoSegueVolta(ArrayList<Conta> naoSegueVolta) {
        this.naoSegueVolta = naoSegueVolta;
    }

    public ArrayList<Conta> getSeguidoresAntigos() {
        return seguidoresAntigos;
    }

    public void setSeguidoresAntigos(ArrayList<Conta> seguidoresAntigos) {
        this.seguidoresAntigos = seguidoresAntigos;
    }

    public ArrayList<Conta> getParouDeSeguir() {
        return parouDeSeguir;
    }

    public void setParouDeSeguir(ArrayList<Conta> parouDeSeguir) {
        this.parouDeSeguir = parouDeSeguir;
    }
}

