/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2l.desktop.gestion.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author nathalie
 */
public class AffichageIntervention {

    private final Intervention intervention;
    private final Intervenant intervenant;
    private final Salle salle;

    public AffichageIntervention(Salle s, Intervenant p, Intervention i )
    {
        this.salle=s;
        this.intervenant=p;
        this.intervention=i;
    }

    public SimpleStringProperty getNomSalleProperty() {
        return new SimpleStringProperty(salle.getNom());
    }

    public SimpleStringProperty getIntervenantProperty() {
        return new SimpleStringProperty(intervenant.toString());
    }
    //ZONE DE MODIFICATION
    public SimpleStringProperty getMotifProperty() {
        return new SimpleStringProperty(intervention.getMotif());
    }

    public SimpleStringProperty getDateProperty() {
        return new SimpleStringProperty(intervention.getDate());
    }
    //ZONE DE MODIFICATION
    public SimpleIntegerProperty getContactProperty() {
        return new SimpleIntegerProperty(intervenant.getTelephone());
    }
}
