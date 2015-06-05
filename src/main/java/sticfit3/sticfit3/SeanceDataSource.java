package sticfit3.sticfit3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffrey on 04/06/2015.
 */
public class SeanceDataSource {
    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteSeance dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteSeance.COLUMN_SEANCE  };

    public SeanceDataSource(Context context) {
        dbHelper = new MySQLiteSeance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //crée un nouvel element (il y a que serie pour le moment)
    public SeanceBDD createComment(String seance) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteSeance.COLUMN_SEANCE, seance);
        long insertId = database.insert(MySQLiteSeance.TABLE_SEANCE, null,
                values);
        Cursor cursor = database.query(MySQLiteSeance.TABLE_SEANCE,
                allColumns, MySQLiteSeance.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        SeanceBDD newSerie = cursorToComment(cursor);
        cursor.close();
        return newSerie;
    }

    // supprimer un element
    public void deleteComment(SeanceBDD seance) {
        long id = seance.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteSeance.TABLE_SEANCE, MySQLiteSeance.COLUMN_ID
                + " = " + id, null);
    }

    public SeanceBDD getCommentById(Long idEx) {
        SeanceBDD comments = new SeanceBDD();
        Cursor cursor = database.query(MySQLiteSeance.TABLE_SEANCE,
                allColumns, MySQLiteSeance.COLUMN_ID + " = " + idEx, null,
                null, null, null);
        cursor.moveToFirst();
        System.out.println("Comment get with id: " + idEx);

        SeanceBDD comment = cursorToComment(cursor);

        cursor.close();
        return comment;

    }
    // recupere le contenu de la bdd
    public List<SeanceBDD> getAllComments() {
        List<SeanceBDD> comments = new ArrayList<SeanceBDD>();
        String orderBy =  MySQLiteHelper.COLUMN_ID + " DESC";
        Cursor cursor = database.query(MySQLiteSeance.TABLE_SEANCE,
                allColumns, null, null, null, null, orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SeanceBDD comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return comments;
    }

    // curseur de modification
    private SeanceBDD cursorToComment(Cursor cursor) {
        SeanceBDD seance = new SeanceBDD();
        seance.setId(cursor.getLong(0));
        seance.setSeance(cursor.getString(1));

        return seance;
    }

}
