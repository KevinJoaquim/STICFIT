package sticfit3.sticfit3;

/**
 * Created by kevin on 28/05/2015.
 */
public class PompeBDD {
        private long id;
        private String serie;
        private String repetition;
        private String calorie;
        private String seance;







    public long getId() {
            return id;
        }

    public void setId(long id) {
        this.id = id;
    }

    public String getserie() {
        return serie;
    }

    public String getRepetition() { return repetition; }

    public void setRepetition(String repetition) { this.repetition = repetition; }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
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

        return "Serie: " + serie + "\nRepetition : " +repetition + "\nCalorie :" +calorie ;
    }
}

