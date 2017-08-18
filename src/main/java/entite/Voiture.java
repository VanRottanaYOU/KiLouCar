package entite;

import java.io.Serializable;

public class Voiture implements Serializable {
	
	private String marque;
	private String modele;
	private String immatriculation;
	private String carburant;
	private boolean status;
	private static final long serialVersionUID = 1L;
	
	
	public Voiture(String immatriculation, String marque, String modele, String carburant, boolean status) {
		
		this.marque = marque;
		this.modele = modele;
		this.immatriculation = immatriculation;
		this.carburant = carburant;
		this.status = status;
	}
	
	public Voiture() {
		super();
	}
	
	public String getMarque() {
		return this.marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public String getModele() {
		return this.modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public String getImmatriculation() {
		return this.immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	public String getCarburant() {
		return this.carburant;
	}
	public void setCarburant(String carburant) {
		this.carburant = carburant;
	}
	public boolean isStatus() {
		return this.status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Voiture [marque=" + this.marque + ", modele=" + this.modele + ", immatriculation="
				+ this.immatriculation + ", carburant=" + this.carburant + ", status=" + this.status + "]";
	}
	
	

}
