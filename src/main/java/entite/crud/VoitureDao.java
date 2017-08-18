package entite.crud;

import java.util.*;

import javax.persistence.Query;

import controle.connection.Connexion;
import entite.Voiture;

public class VoitureDao {

	// Propriété pour établir la connection avec la BD
	// -----------------------------------------------
	private final Connexion laConnexion;
	private static ArrayList<Voiture> listeVoitures = new ArrayList();
	private Voiture maVoiture;

	// CONSTRUCTEUR
	// -------------
	public VoitureDao(Connexion connexion) {
		this.laConnexion = connexion;
		
	}

	// 
	// ------------------------------------
	public void creer(Voiture voiture) {
		listeVoitures.add(voiture);
	}

	/*
	 * Lecture et récupération des enregistrements de la BD
	 */
	@SuppressWarnings("unchecked")
	public  List<Voiture> lire() {
		return listeVoitures;
	}

	public Voiture lire(String immatrticulation) {
		maVoiture = new Voiture();
		for (int i=0;i<listeVoitures.size();i++)
		{
			if (listeVoitures.get(i).getImmatriculation().equals(immatrticulation))
			{
				maVoiture = listeVoitures.get(i);
				return maVoiture;
			}
		}
		return maVoiture;
	}
	
	public void louer(String immatrticulation) {
		maVoiture = new Voiture();
		for (int i=0;i<listeVoitures.size();i++)
		{
			if (listeVoitures.get(i).getImmatriculation().equals(immatrticulation))
			{
				listeVoitures.get(i).setStatus(true);
			}
		}
	}
	
	
	public void rentrer(String immatrticulation) {
		maVoiture = new Voiture();
		for (int i=0;i<listeVoitures.size();i++)
		{
			if (listeVoitures.get(i).getImmatriculation().equals(immatrticulation))
			{
				listeVoitures.get(i).setStatus(false);
			}
		}
	}
	
	public ArrayList<Voiture> rechercherMarque(String marque) {
		ArrayList<Voiture> listeVoituresMarque = new ArrayList();

		for (int i=0;i<listeVoitures.size();i++)
		{
			if (listeVoitures.get(i).getMarque().equals(marque) && !listeVoitures.get(i).isStatus())
			{
				listeVoituresMarque.add(listeVoitures.get(i));
			}
		}
		System.out.println("crud listeVoituresMarque : " +listeVoituresMarque);
		return listeVoituresMarque;
	}

	// -----------------------------------
	public boolean modifier(Voiture leVoiture) {
		boolean bool= false;
		for (int i=0;i<listeVoitures.size();i++)
		{
			if (listeVoitures.get(i).getImmatriculation().equals(leVoiture.getImmatriculation()))
			{
				listeVoitures.get(i).setMarque(leVoiture.getMarque());
				listeVoitures.get(i).setModele(leVoiture.getModele());
				listeVoitures.get(i).setCarburant(leVoiture.getCarburant());
				maVoiture = listeVoitures.get(i);
				bool = true;
				return bool;
			}
		}
		
		return bool;

	}


	// ----------------------------------
	public boolean supprimer(String immatricualtion) {
		maVoiture = new Voiture();
		for (int i=0;i<listeVoitures.size();i++)
		{
			if (listeVoitures.get(i).getImmatriculation().equals(immatricualtion))
			{				
				maVoiture = listeVoitures.remove(i);
				return true;
			}
		}
		
		return false;

	}


	// --------------------
	@SuppressWarnings("unchecked")
	public Voiture  chercher(String immatricualtion) {
		Voiture maVoiture = new Voiture();
		return maVoiture;

	}
}
