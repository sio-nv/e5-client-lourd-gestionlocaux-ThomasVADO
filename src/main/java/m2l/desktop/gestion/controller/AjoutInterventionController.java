package m2l.desktop.gestion.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import m2l.desktop.gestion.model.Model_Salle;

import java.net.URL;
import java.util.ResourceBundle;

public class AjoutInterventionController implements Initializable {
    @FXML
    public ComboBox<String> salleComboBox;
    @FXML
    public ComboBox<String> intervenantComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model_Salle.connect_to_database();
        Model_Salle.selectSalles();
        loadSalles();
    }

    private void loadSalles() {
        salleComboBox.setItems(Model_Salle.getNomsSalles());
    }

}
