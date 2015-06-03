package sticfit3.sticfit3;

/**
 * Created by kevin on 03/06/2015.
 */
public class PersonneBDD {

    private long id;
    private String sexe;
    private String age;
    private String poids;
    private String taille;


    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {

        return sexe + taille + age + poids ;
    }
}


