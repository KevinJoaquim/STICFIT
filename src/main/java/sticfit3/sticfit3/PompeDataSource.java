package sticfit3.sticfit3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 28/05/2015.
 */
public class PompeDataSource {
    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_SERIE };

    public PompeDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //crée un nouvel element (il y a que serie pour le moment)
    public PompeBDD createComment(String serie) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SERIE, serie);
        long insertId = database.insert(MySQLiteHelper.TABLE_POMPES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_POMPES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        PompeBDD newSerie = cursorToComment(cursor);
        cursor.close();
        return newSerie;
    }

    // supprimer un element
    public void deleteComment(PompeBDD pompe) {
        long id = pompe.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_POMPES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    // recupere le contenu de la bdd
    public List<PompeBDD> getAllComments() {
        List<PompeBDD> comments = new ArrayList<PompeBDD>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_POMPES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PompeBDD comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return comments;
    }

    // curseur de modification
    private PompeBDD cursorToComment(Cursor cursor) {
        PompeBDD pompe = new PompeBDD();
        pompe.setId(cursor.getLong(0));
        pompe.setSerie(cursor.getString(1));
        return pompe;
    }

}
