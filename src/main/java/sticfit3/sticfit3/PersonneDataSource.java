package sticfit3.sticfit3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 03/06/2015.
 */
public class PersonneDataSource {
    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLitePerso dbHelper;
    private String[] allColumns = { MySQLitePerso.COLUMN_ID,
            MySQLitePerso.COLUMN_SEXE, MySQLitePerso.COLUMN_TAILLE, MySQLitePerso.COLUMN_AGE, MySQLitePerso.COLUMN_POIDS };

    public PersonneDataSource(Context context) {
        dbHelper = new MySQLitePerso(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //crée un nouvel element (il y a que serie pour le moment)
    public PersonneBDD createPerso(String sexe,String taille, String age, String poids) {
        ContentValues values = new ContentValues();
        values.put(MySQLitePerso.COLUMN_SEXE, sexe);
        values.put(MySQLitePerso.COLUMN_TAILLE, taille);
        values.put(MySQLitePerso.COLUMN_AGE, age);
        values.put(MySQLitePerso.COLUMN_POIDS, poids);
        long insertId = database.insert(MySQLitePerso.TABLE_PERSONNE, null,
                values);
        Cursor cursor = database.query(MySQLitePerso.TABLE_PERSONNE,
                allColumns, MySQLitePerso.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        PersonneBDD newInfo = cursorToComment(cursor);
        cursor.close();
        return newInfo;
    }

    // supprimer un element
    public void deleteComment(PersonneBDD personne) {
        long id = personne.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLitePerso.TABLE_PERSONNE, MySQLitePerso.COLUMN_ID
                + " = " + id, null);
    }

    // recupere le contenu de la bdd
    public List<PersonneBDD> getAllComments() {
        List<PersonneBDD> info = new ArrayList<PersonneBDD>();

        Cursor cursor = database.query(MySQLitePerso.TABLE_PERSONNE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PersonneBDD comment = cursorToComment(cursor);
            info.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return info;
    }

    // curseur de modification
    private PersonneBDD cursorToComment(Cursor cursor) {
        PersonneBDD info = new PersonneBDD();
        info.setId(cursor.getLong(0));
        info.setSexe(cursor.getString(1));
        info.setTaille(cursor.getString(2));
        info.setAge(cursor.getString(3));
        info.setPoids(cursor.getString(4));
        return info;
    }

}

