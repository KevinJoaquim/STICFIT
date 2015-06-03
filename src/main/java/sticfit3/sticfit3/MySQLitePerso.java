package sticfit3.sticfit3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kevin on 03/06/2015.
 */
public class MySQLitePerso extends SQLiteOpenHelper {
    // page BDD en mode SQLite

    public static final String TABLE_PERSONNE = "personne";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SEXE = "sexe";
    public static final String COLUMN_TAILLE = "taille";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_POIDS = "poids";

    private static final String DATABASE_NAME = "infoperso.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PERSONNE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SEXE
            + " text not null"+ COLUMN_TAILLE +"text not null"+ COLUMN_AGE + "text not null" + COLUMN_POIDS + "text not null );";

    public MySQLitePerso(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNE);
        onCreate(db);
    }
}

