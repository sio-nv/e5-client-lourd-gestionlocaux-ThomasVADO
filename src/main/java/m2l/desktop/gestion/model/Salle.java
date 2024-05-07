package m2l.desktop.gestion.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author nathalie
 */
public class Salle {

    protected int num;
    protected String nom;
    protected int capacite;
    public Rectangle r;
    public String batiment;


    /**
     * Constructeur qui initialise le nom
     * @param n : le nom de la salle
     */
    public Salle(String n){
        this.nom = n;
    }

    public Salle(String n, int c) {
        this.nom = n;
        this.capacite = c;
    }

    public Salle(String n, Rectangle r, String b)
    {
        this.nom = n;
        this.r = r;
        this.batiment = b;
    }

    public Salle(int numSalle, String nomSalle) {
        this.num = numSalle;
        this.nom = nomSalle;
    }

    public String getNom() {
        return nom;
    }
    public int getNum() {
        return num;
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite=capacite;
    }

    public void setNomSalle(String nomSalle) {
        this.nom=nomSalle;
    }
}
