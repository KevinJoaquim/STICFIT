package sticfit3.sticfit3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kevin on 05/06/2015.
 */
public class MySQLiteInfo extends SQLiteOpenHelper {

    public static final String TABLE_INFO = "info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_POIDS = "poids";
    public static final String COLUMN_TAILLE = "taille";
    public static final String COLUMN_SEXE = "sexe";
    public static final String COLUMN_AGE = "age";


    private static final String DATABASE_NAME = "info.db";
    private static final int DATABASE_VERSION = 9;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_INFO + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_POIDS + " text not null,"
            + COLUMN_TAILLE + " text not null,"
            + COLUMN_SEXE + " text not null,"
            + COLUMN_AGE + " text not null);";

    public MySQLiteInfo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteInfo.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        onCreate(db);
    }
}
