package controle.modele;

/*
 *  Classe comportant le modèle de données des Clients.
 *  Doit étendre la classe abstraite AbstractTableModel
 */

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entite.Voiture;

public class ModeleVoitures extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	// représente les lignes du modèle
	private final List<Voiture> lesDonnees;

	// les entêtes de colonnes
	private static final String[] TITRES = {"Immatriculation", "Marque", "Modele", "Carburant", "Status location"};

	public ModeleVoitures(List<Voiture> lesVoitures) {
    	lesDonnees = new ArrayList<>(lesVoitures);
	}
	
	public int getRowCount() {
		return lesDonnees.size();
	}

	public int getColumnCount() {
		return TITRES.length;
	}
	
	public String getColumnName(int columnIndex) {
		return TITRES[columnIndex];
	}

	// pour accéder à la valeur d'une cellule
	 public Object getValueAt(int rowIndex, int columnIndex) {
	    	Voiture uneVoiture = getVoiture(rowIndex);
			switch(columnIndex){
				case 0:
				    return uneVoiture.getImmatriculation();
				case 1:
				    return uneVoiture.getMarque();
				case 2:
				    return uneVoiture.getModele();
				case 3:
				    return uneVoiture.getCarburant();
				case 4:
				    if (uneVoiture.isStatus()){
				    	return "louée";
				    }else {
				    	return "disponible";
				    }
				default:
				    return null;
			}
	    }
	
	 public Voiture getVoiture(int ligne) {
			return lesDonnees.get(ligne);
		}

	/*
	 * utiles pour les renderers par défaut qui vont appliquer
	 * un style de présentation des données en fonction de la classe
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex){
		Class<?> classe = null;
		switch(columnIndex){
//		case 0:
//		case 1:
//		case 2:
//		case 3:
//			classe = String.class;
//			break;
		case 4:
			classe = String.class;
			break;
		default:
			classe = super.getColumnClass(columnIndex);
			break;
		}
		return classe;
	}

	// pour obtenir le numéro de ligne à partir du code
	// lors d'une recherche dans la liste
	private int getNumLigne(String immatriculation) {
		int numLigne = -1;

		for (int idx = 0; idx < lesDonnees.size(); ++idx) {
			String immatriculationVoiture = lesDonnees.get(idx).getImmatriculation();
			if(immatriculationVoiture.equals(immatriculation)) {
				numLigne = idx;
				break;
			}
		}    		
		return numLigne;    	
	}    

	// -------------------------
	 public void créé(Voiture uneVoiture) {
			lesDonnees.add(uneVoiture);
			int ligne = lesDonnees.size() -1;
			fireTableRowsInserted(ligne, ligne);
	    }

	public void supprimé(int indexLigne) {
		lesDonnees.remove(indexLigne);
		// notification de la suppression de la ligne indexLigne à la ligne indexLigne
		fireTableRowsDeleted(indexLigne, indexLigne);
	}

	public void modifié(Voiture uneVoiture) {
		int numeroLigne = getNumLigne(uneVoiture.getImmatriculation());
		if (numeroLigne >= 0) {			
			lesDonnees.set(numeroLigne, uneVoiture);
			fireTableRowsUpdated(numeroLigne, numeroLigne);
		}
	}

	/* permet de mettre à jour le modèle suite à de nouvelles recherches
	 * et d'informer les vues affichant ce modèle
	 */
	public void lu(Voiture nouvellesDonnees){
		lesDonnees.clear();
		lesDonnees.add(nouvellesDonnees);
		fireTableDataChanged();
	}
	
	public void luAll(List<Voiture> nouvellesDonnees){
		lesDonnees.clear();
		lesDonnees.addAll(nouvellesDonnees);
		fireTableDataChanged();
	}
	
	
}
