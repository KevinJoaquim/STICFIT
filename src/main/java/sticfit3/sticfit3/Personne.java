package sticfit3.sticfit3;

/**
 * Created by kevin on 29/04/2015.
 */
public class Personne {

    private int id;
    private String Poids;
    private String Taille;
    private String Age;

    public Personne(){}

    public  Personne(String Poids, String Taille, String Age){
        this.Poids = Poids;
        this.Taille = Taille;
        this.Age = Age;
     }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPoids() {
        return Poids;
    }

    public void setPoids(String Poids) {
        this.Poids = Poids;
    }
    public String getTaille() {
        return Taille;
    }

    public void setTaille(String Taille) {
        this.Taille = Taille;
    }
    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }
    public String toString(){
        return "ID : "+id+"\nPoids : "+Poids+"\nTaille : "+Taille+"\nAge : "+Age;
    }


}
