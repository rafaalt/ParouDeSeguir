package pack.rafaalt.app.paroudeseguir.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ParouDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "PAROU_DB_V2";
    public static final int DB_VERSION = 1;

    public static final String ID_COLUMN = "id";
    public static final String USERNAME_COLUMN = "username";
    public static final String NAME_COLUMN = "nomeCompleto";
    public static final String VERIFIED_COLUMN = "isVerified";
    public static final String PRIVATE_COLUMN = "isPrivate";
    public static final String FOTO_COLUMN = "iconUrl";

    public ParouDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
