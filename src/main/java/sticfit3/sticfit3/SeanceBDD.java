package sticfit3.sticfit3;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by geoffrey on 04/06/2015.
 */
public class SeanceBDD  {
    private long id;
    private String seance;
    private String dateSeance;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeance() {
        return seance;
    }


    public void setSeance(String seance) {
        this.seance = seance;
    }

    public String getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(String dateSeance) {
        this.dateSeance = dateSeance;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {


        return "Seance : " + seance + "\nDu : "+ dateSeance;
    }
}


