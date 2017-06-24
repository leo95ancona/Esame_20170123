/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryVicini;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	String annoS = txtAnno.getText();
    	
    	if (annoS.length() == 0){
    		txtResult.appendText("ERRORE : devi inserire un anno\n");
    		return;
    	}
    	
    	int anno;
    	try{
    		anno = Integer.parseInt(annoS);
    	}
    	catch (NumberFormatException e) {
    		txtResult.appendText("ERRORE : devi inserire un anno\n");
    		return;
    	}
    	
    	if (anno<1816){
    		txtResult.appendText("ERRORE : per anni inferiorio a 1816 non ci sono dati\n");
    		return;
    	}
    	
    	txtResult.appendText(model.generaGrafo(anno)+"\n");
    	
    	//popolo la tendina con le country appena stampate
    	boxNazione.getItems().clear();
    	for (CountryVicini cv : model.getLista()){
    		boxNazione.getItems().add(cv.getCountry());
    	}
    	Collections.sort(boxNazione.getItems());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	Country country = boxNazione.getValue();
    	if (country==null){
    		txtResult.appendText("ERRORE : scegliere un paese\n");
    	}
    	
    	int passi = model.simula(country);
    	
    	List<CountryVicini> lista = model.getLista2();
    	String s="";
    	for (CountryVicini cv : lista){
    		s+= cv.getCountry().getStateName()+" "+cv.getVicini()+"\n";
    	}
    	
    	txtResult.appendText("Numero passi:"+passi+"\n");
    	txtResult.appendText("LISTA Paese-N°STANZIATI\n");
    	txtResult.appendText(s);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
	}
}
