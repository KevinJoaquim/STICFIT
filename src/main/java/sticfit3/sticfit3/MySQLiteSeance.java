package sticfit3.sticfit3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by geoffrey on 04/06/2015.
 */
public class MySQLiteSeance extends SQLiteOpenHelper {
    // page BDD en mode SQLite

    public static final String TABLE_SEANCE = "seance";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SEANCE = "seance";

    private static final String DATABASE_NAME = "seance.db";
    private static final int DATABASE_VERSION = 2;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SEANCE + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_SEANCE + " text not null );";

    public MySQLiteSeance(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteSeance.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEANCE);
        onCreate(db);
    }
}

