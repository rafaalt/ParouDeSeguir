package pack.rafaalt.app.paroudeseguir.model;

import java.io.Serializable;

public class Conta implements Serializable {
    private String username;
    private String nomeCompleto;
    private String iconUrl;
    private boolean isPrivate;
    private boolean isVerified;
    private long id;

    public Conta(String username, String nomeCompleto, String iconUrl, boolean isPrivate, boolean isVerified, long id) {
        this.username = username;
        this.nomeCompleto = nomeCompleto;
        this.iconUrl = iconUrl;
        this.isPrivate = isPrivate;
        this.isVerified = isVerified;
        this.id = id;
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

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        Conta conta = (Conta) o;
        if(this.id == conta.getId())
            return true;
        else
            return false;
    }

    public int intVerified(){
        int retorno = ( (this.isVerified) ? 1 : 0);

        return retorno;
    }

    public int intPrivado(){
        int retorno = ( (this.isPrivate) ? 1 : 0);

        return retorno;
    }

    @Override
    public String toString() {
        return username + "   " + nomeCompleto;
    }
}
