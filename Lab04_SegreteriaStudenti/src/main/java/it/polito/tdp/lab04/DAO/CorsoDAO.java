package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	HashMap<Studente,Corso> mappac=new HashMap<>();
List<Corso> listac=new ArrayList<>();
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 * 
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				/*String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
			}
			*/
				//PRENDO VALORI DA HEIDISQL E LI AGGIUNGO
			Corso s = new Corso(rs.getString("codins"), rs.getString("nome"),  rs.getInt("crediti"), rs.getInt("pd"));
			corsi.add(s);
		}
			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		/*String sql="Select* from corso where codins=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				mappac.get(corso.getCodins());
				conn.close();//PRIMA DI OGNI RETURN
				
			}
		
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	*/	
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
			 final String sql="SELECT * FROM iscrizione, studente WHERE iscrizione.matricola=studente.matricola AND codins=?";

		List<Studente> studenti=new LinkedList<>();
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs=st.executeQuery();
			while (rs.next()) {
				Studente s=new Studente(rs.getInt("matricola"),rs.getString("nome"),rs.getString("cognome"),rs.getString("cDS"));
				
				studenti.add(s);
			}
			conn.close();
			
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return studenti;
		
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		String sql="INSERT IGNORE INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES(?,?)";
		boolean returnValue = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
            st.setString(2, corso.getCodins());
			
			int res = st.executeUpdate();	

			if (res == 1) {
				returnValue = true;

			conn.close();
			
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		 return returnValue;
	
		
	}

}
