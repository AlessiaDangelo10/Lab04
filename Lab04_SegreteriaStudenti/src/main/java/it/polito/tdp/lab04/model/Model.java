package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	private CorsoDAO dao;
	private StudenteDAO studenteDAO;
	
	public Model() {
		dao = new CorsoDAO();
		studenteDAO=new StudenteDAO();
	}
	public List<Corso> getTuttiICorsi() {

		return dao.getTuttiICorsi();
		}
	public void getCorso(Corso corso) {
		dao.getCorso(corso);
	}

	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return dao.inscriviStudenteACorso(studente, corso);
	}
	
	public Studente getStudente(int matricola) {
		return studenteDAO.getStudente(matricola);
	}
	public List<Corso> cercaCorsiDatoStudente(Studente studente){
		return studenteDAO.getCorsiFromStudente(studente);
	} 
	
	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {
		return studenteDAO.isStudenteIscrittoACorso(studente,corso);
	}
	public List<Studente> studentiIscrittiAlCorso(Corso corso) {
		return dao.getStudentiIscrittiAlCorso(corso);
	}
}
