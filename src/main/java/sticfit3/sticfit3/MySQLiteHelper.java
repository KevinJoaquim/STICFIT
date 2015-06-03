package sticfit3.sticfit3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kevin on 28/05/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // page BDD en mode SQLite

    public static final String TABLE_POMPES = "pompes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SERIE = "serie";

    private static final String DATABASE_NAME = "exercice.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_POMPES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SERIE
            + " text not null);";

    public MySQLiteHelper(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POMPES);
        onCreate(db);
    }
}

