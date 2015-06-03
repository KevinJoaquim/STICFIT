package sticfit3.sticfit3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevin on 29/04/2015.
 */
public class MaBaseSQLite extends SQLiteOpenHelper {
    private static final String TABLE_PERSONNE = "table_personne";
    private static final String COL_ID = "ID";
    private static final String COL_POIDS = "Poids";
    private static final String COL_TAILLE = "Taille";
    private static final String COL_AGE = "Age";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PERSONNE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_POIDS + " TEXT NOT NULL, "
            + COL_TAILLE + " TEXT NOT NULL, "+ COL_AGE + " TEXT NOT NULL);";

    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_PERSONNE + ";");
        onCreate(db);
    }

}
