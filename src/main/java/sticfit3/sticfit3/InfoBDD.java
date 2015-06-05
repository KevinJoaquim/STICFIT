package sticfit3.sticfit3;

/**
 * Created by kevin on 05/06/2015.
 */
public class InfoBDD {
    private Long id;
    private String poids;
    private String taille;
    private String sexe;
    private String age;

    public InfoBDD(){}

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public InfoBDD(String poids, String taille){
        this.poids = poids;
        this.taille = taille;
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

    public String toString(){
        return "\n\n\nPoids : "+poids+"\n\n\nTaille : "+taille+"\n\n\nAge : "+age+"\n\n\nSexe : "+sexe;
    }
}

