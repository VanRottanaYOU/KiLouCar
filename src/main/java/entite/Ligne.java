package entite;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="LignesCommandes")
public class Ligne implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private String id;


	private int quantite;

	/*
	 * Constructeur 1
	 */


	public Ligne() {
		super();
	}


	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}



	
}
