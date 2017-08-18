package controle.etat;

import java.util.List;

import controle.connection.Connexion;
import entite.Voiture;
import entite.crud.VoitureDao;

public class FabriqueVoitures {

	public static List<Voiture> createBeanCollection() {
		Connexion connexion = Connexion.getConnexion();
		VoitureDao crud = new VoitureDao(connexion);
		List<Voiture> Voitures = crud.lire();
		return Voitures;
	}
}
