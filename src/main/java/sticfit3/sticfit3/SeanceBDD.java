package sticfit3.sticfit3;

/**
 * Created by geoffrey on 04/06/2015.
 */
public class SeanceBDD  {
    private long id;
    private String seance;


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


    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {

        return "Nom Seance: " + seance ;
    }
}


