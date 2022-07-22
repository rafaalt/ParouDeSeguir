package pack.rafaalt.app.paroudeseguir.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pack.rafaalt.app.paroudeseguir.model.Conta;

public class SeguidoresDAO {

    private SeguidoresDB seguidoresDB;
    public SeguidoresDAO(SeguidoresDB segDB){
        this.seguidoresDB = segDB;
    }

    public void criarTabela(String id){
        String script = "CREATE TABLE IF NOT EXISTS " + id + " (\n" +
                "\tid TEXT PRIMARY KEY NOT NULL, \n" +
                "\tusername TEXT NOT NULL, \n" +
                "\tnomeCompleto TEXT, \n" +
                "\tisVerified INTEGER, \n" +
                "\tisPrivate INTEGER, \n" +
                "\ticonUrl TEXT);";

        SQLiteDatabase writeDb = this.seguidoresDB.getWritableDatabase();
        writeDb.execSQL(script);
        writeDb.close();
    }

    public void preencherTabela(String id, ArrayList<Conta> contas){

        criarTabela(id);
        SQLiteDatabase writeDb = this.seguidoresDB.getWritableDatabase();

        try{

            for(Conta conta : contas){
                ContentValues contentValues = new ContentValues();
                contentValues.put(SeguidoresDB.ID_COLUMN, conta.getId() + "");
                contentValues.put(SeguidoresDB.NAME_COLUMN, conta.getNomeCompleto());
                contentValues.put(SeguidoresDB.USERNAME_COLUMN, conta.getUsername());
                contentValues.put(SeguidoresDB.VERIFIED_COLUMN, conta.intVerified());
                contentValues.put(SeguidoresDB.PRIVATE_COLUMN, conta.intPrivado());
                contentValues.put(SeguidoresDB.FOTO_COLUMN, conta.getIconUrl());

                writeDb.insert(id, null, contentValues);
            }

        }catch (Exception e){

        }finally {
            writeDb.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Conta> getSeguidores(String idUser){
        ArrayList<Conta> retorno = new ArrayList<>();
        SQLiteDatabase readDB = this.seguidoresDB.getReadableDatabase();

        try {
            Cursor res = readDB.query(idUser, null, null, null, null, null, null);
            if (res.moveToFirst()) {
                do {
                    long id = Long.parseLong(res.getString(res.getColumnIndex(SeguidoresDB.ID_COLUMN)));
                    String name = res.getString(res.getColumnIndex(SeguidoresDB.NAME_COLUMN));
                    String username = res.getString(res.getColumnIndex(SeguidoresDB.USERNAME_COLUMN));
                    int privado = res.getInt(res.getColumnIndex(SeguidoresDB.PRIVATE_COLUMN));
                    int verificado = res.getInt(res.getColumnIndex(SeguidoresDB.VERIFIED_COLUMN));
                    String foto = res.getString(res.getColumnIndex(SeguidoresDB.FOTO_COLUMN));

                    boolean isPrivate = (privado != 0);
                    boolean isVerified = (privado != 0);

                    retorno.add(new Conta(username, name, foto, isPrivate, isVerified, id));
                } while (res.moveToNext());
            }
        } catch(Exception e) {
            retorno = null;
        } finally {
            readDB.close();
        }
        return retorno;
    }

    public void deleteTable(String idUser){
        SQLiteDatabase deleteDB = this.seguidoresDB.getWritableDatabase();
        deleteDB.delete(idUser, null, null);
        deleteDB.close();
    }

}
