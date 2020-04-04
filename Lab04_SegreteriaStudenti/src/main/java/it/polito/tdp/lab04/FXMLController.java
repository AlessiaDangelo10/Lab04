package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class FXMLController {
	private Model model;	
	private List<Corso> corsi;
		

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboCorso;

    @FXML
    private Button btnCeracIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private CheckBox txtDefoult;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button btnReset;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	txtRisultato.clear();

	try {

		int matricola = Integer.parseInt(txtMatricola.getText());

		Studente studente = model.getStudente(matricola);
		if (studente == null) {
			txtRisultato.appendText("Nessun risultato: matricola inesistente");
			return;
		}

		List<Corso> corsi = model.cercaCorsiDatoStudente(studente);

		StringBuilder sb = new StringBuilder();

		for (Corso corso : corsi) {
			sb.append(String.format("%-8s ", corso.getCodins()));
			sb.append(String.format("%-4s ", corso.getCrediti()));
			sb.append(String.format("%-45s ", corso.getNome()));
			sb.append(String.format("%-4s ", corso.getPd()));
			sb.append("\n");
		}
		txtRisultato.appendText(sb.toString());

	} catch (NumberFormatException e) {
		txtRisultato.setText("Inserire una matricola nel formato corretto.");
	} catch (RuntimeException e) {
		txtRisultato.setText("ERRORE DI CONNESSIONE AL DATABASE!");
	}



    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	try {

			Corso corso = comboCorso.getValue();
			if (corso == null) {
				txtRisultato.setText("Selezionare un corso.");
				return;
			}

			List<Studente> studenti = model.studentiIscrittiAlCorso(corso);

			StringBuilder sb = new StringBuilder();

			for (Studente studente : studenti) {

				sb.append(String.format("%-10s ", studente.getMatricola()));
				sb.append(String.format("%-20s ", studente.getCognome()));
				sb.append(String.format("%-20s ", studente.getNome()));
				sb.append(String.format("%-10s ", studente.getcDS()));
				sb.append("\n");
			}

			txtRisultato.appendText(sb.toString());

		} catch (RuntimeException e) {
			txtRisultato.setText("error!");
		}
    	
    }

    @FXML
    void doComplete(ActionEvent event) {
    	txtRisultato.clear();
		txtNome.clear();
		txtCognome.clear();

		try {

			int matricola = Integer.parseInt(txtMatricola.getText());
			Studente studente = model.getStudente(matricola);

			if (studente == null) {
				txtRisultato.appendText(" matricola inesistente");
				return;
			}

			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());

		} catch (NumberFormatException e) {
			txtRisultato.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtRisultato.setText("error!");
		}


    }

  

    @FXML
    void doIscrivi(ActionEvent event) {
    	txtRisultato.clear();

		try {

			if (txtMatricola.getText().isEmpty()) {
				txtRisultato.setText("Inserire una matricola.");
				return;
			}

			if (comboCorso.getValue() == null) {
				txtRisultato.setText("Selezionare un corso.");
				return;
			}

			// Prendo la matricola in input
			int matricola = Integer.parseInt(txtMatricola.getText());

			// mi basterebbe la matricola per fare l'iscrizione ma per completezza 
			// posso anche far apparire il Nome e Cognome dello studente nell'interfaccia
			Studente studente = model.getStudente(matricola);
			if (studente == null) {
				txtRisultato.appendText("Nessun risultato: matricola inesistente");
				return;
			}

			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());

			// Ottengo il nome del corso
			Corso corso = comboCorso.getValue();

			// Controllo se lo studente è già iscritto al corso
			if (model.isStudenteIscrittoACorso(studente, corso)) {
				txtRisultato.appendText("Studente già iscritto a questo corso");
				return;
			}
			// Prima di passare a rendere il tasto 'Iscrivi' realmente operativo 
			// con l'iscrizione, la versione 'Cerca' avrebbe fatto solo il successivo else.
			//}else {
			//	txtResult.appendText("Studente non iscritto a questo corso");
			//	return;
			//}

			///*
			// Iscrivo lo studente al corso.
			// Controllo che l'inserimento vada a buon fine
			if (!model.inscriviStudenteACorso(studente, corso)) {
				txtRisultato.appendText("Errore durante l'iscrizione al corso");
				return;
			} else {
				txtRisultato.appendText("Studente iscritto al corso!");
			}
			//*/

		} catch (NumberFormatException e) {
			txtRisultato.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtRisultato.setText("error!");
		}
    	

    }

    @FXML
    void doReset(ActionEvent event) {
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtRisultato.clear();
    	comboCorso.getSelectionModel().clearSelection();

    }
    //PRENDERE VALORI CON COMBOBOX DI HEIDISQL
    private void setComboItems() {
    	//ottieni tutti i corsi del model
    	corsi = model.getTuttiICorsi();
    	
    	//Aggiungi tutti i corsi alla ComboBox. 
    	Collections.sort(corsi); // per avere maggiore ordine li sorto alfabeticamente
    	comboCorso.getItems().addAll(corsi); // richiama il toString dell'oggetto Corso
    }
    
    @FXML
    void initialize() {
    	assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCeracIscrittiCorso != null : "fx:id=\"btnCeracIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDefoult != null : "fx:id=\"txtDefoult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        txtRisultato.setStyle("-fx-font-family: monospace");
    }



	public void setModel(Model model) {
		this.model=model;
		setComboItems();
		
	}
}
