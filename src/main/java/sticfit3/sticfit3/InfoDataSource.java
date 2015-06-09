package sticfit3.sticfit3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 05/06/2015.
 */
public class InfoDataSource {
    private SQLiteDatabase database;
    private MySQLiteInfo dbHelper;
    private String[] allColumns = { MySQLiteInfo.COLUMN_ID,
            MySQLiteInfo.COLUMN_POIDS,MySQLiteInfo.COLUMN_TAILLE,MySQLiteInfo.COLUMN_SEXE,
            MySQLiteInfo.COLUMN_AGE };



    public InfoDataSource(Context context) {
        dbHelper = new MySQLiteInfo(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //crée un nouvel element InfoBDD
    public InfoBDD createComment(String poids, String taille, String sexe, String age) {

        //Récupère dans un Cursor les valeurs de toutes les colonnes de table Info
        Cursor cursor = database.query(MySQLiteInfo.TABLE_INFO,
                allColumns, null, null,
                null, null, null);


        //Si le cursor est vide alors on crée un nouvel élément
        //Sinon on Modifie le contenu à l'id 1
        if(cursor.getCount()== 0){

            ContentValues values = new ContentValues();
            values.put(MySQLiteInfo.COLUMN_POIDS, poids);
            values.put(MySQLiteInfo.COLUMN_TAILLE, taille);
            values.put(MySQLiteInfo.COLUMN_SEXE, sexe);
            values.put(MySQLiteInfo.COLUMN_AGE, age);
            long insertId = database.insert(MySQLiteInfo.TABLE_INFO, null,
                    values);
            Cursor cursorInsert = database.query(MySQLiteInfo.TABLE_INFO,
                    allColumns, MySQLiteInfo.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursorInsert.moveToFirst();
            InfoBDD newSerie = cursorToComment(cursorInsert);
            cursorInsert.close();
            return newSerie;


        }else {

            ContentValues values = new ContentValues();
            values.put(MySQLiteInfo.COLUMN_POIDS, poids);
            values.put(MySQLiteInfo.COLUMN_TAILLE, taille);
            values.put(MySQLiteInfo.COLUMN_SEXE, sexe);
            values.put(MySQLiteInfo.COLUMN_AGE, age);

            long insertId = database.update(MySQLiteInfo.TABLE_INFO, values, MySQLiteInfo.COLUMN_ID + " = " + 1, null);
            Cursor cursorUpdate = database.query(MySQLiteInfo.TABLE_INFO,
                    allColumns, MySQLiteInfo.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursorUpdate.moveToFirst();
            InfoBDD newInfo = cursorToComment(cursorUpdate);
            cursorUpdate.close();
            cursor.close();
            return newInfo;
        }

    }
    //Renvoi le contenu d'un object de la bdd avec l'id
    public InfoBDD getCommentById(long idEx){
        InfoBDD comments = new InfoBDD();
        Log.i("all","=  " + allColumns);
        Cursor cursorPoids = database.query(MySQLiteInfo.TABLE_INFO,
                allColumns, MySQLiteInfo.COLUMN_ID + " = " + idEx, null,
                null, null, null);
        cursorPoids.moveToFirst();
        System.out.println("Comment get with id: " + idEx);

        InfoBDD comment = cursorToComment(cursorPoids);

        cursorPoids.close();
        return comment;


    }


    // supprimer un element
    public void deleteComment(InfoBDD info) {
        long id = info.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteInfo.TABLE_INFO, MySQLiteInfo.COLUMN_ID
                + " = " + id, null);
    }
    // recupere le contenu de la bdd
    public List<InfoBDD> getAllComments() {
        List<InfoBDD> comments = new ArrayList<InfoBDD>();

        Cursor cursor = database.query(MySQLiteInfo.TABLE_INFO,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InfoBDD comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return comments;
    }

    // curseur de modification
    private InfoBDD cursorToComment(Cursor cursor) {
        InfoBDD pompe = new InfoBDD();
        pompe.setId(cursor.getLong(0));
        pompe.setPoids(cursor.getString(1));
        pompe.setTaille(cursor.getString(2));
        pompe.setSexe(cursor.getString(3));
        pompe.setAge(cursor.getString(4));
        return pompe;
    }



}

