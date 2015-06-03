package sticfit3.sticfit3;

/**
 * Created by kevin on 28/05/2015.
 */
public class PompeBDD {
        private long id;
        private String serie;


     public long getId() {
            return id;
        }

    public void setId(long id) {
        this.id = id;
    }

    public String getserie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {

        return "Nombre Serie: " + serie ;
    }
}

