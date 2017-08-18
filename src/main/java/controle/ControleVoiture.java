package controle;


import java.awt.Window;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import controle.connection.Connexion;
import controle.etat.JasperFacade;
import controle.modele.ModeleVoitures;
import dialogue.FExport;
import entite.Voiture;
import entite.crud.VoitureDao;

public class ControleVoiture {

	private  VoitureDao crud;
	private  ModeleVoitures leModeleVoitures;

	private final PropertyChangeSupport notifications = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.notifications.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.notifications.removePropertyChangeListener(listener);
	}

	public ControleVoiture(Connexion connexion) {
		crud = new VoitureDao(connexion);
		List<Voiture> voitures = null;
		try { 
			voitures = crud.lire();
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, 
					"Aucune lecture effectuée dans la liste des voitures.\n\n" +e.getMessage(),
					"Problème rencontré", 
					JOptionPane.ERROR_MESSAGE);        
			voitures = Collections.emptyList();
		}

		leModeleVoitures = new ModeleVoitures(voitures);
	}

	public ModeleVoitures getModele() {
		return leModeleVoitures;
	}

	public Voiture creer (String immatriculation, String marque, String modele, String carburant, boolean status) {

		// création d'une instance voiture pour obtenir le CRUD
		Voiture laVoiture = new Voiture(immatriculation, marque, modele, carburant, status);
		try {
			// 1. sauvegarde d'abord dans la BD
			crud.creer(laVoiture);
			JOptionPane.showMessageDialog(
					null,
					"La voiture a été crée.\n\n"
							, "",
					JOptionPane.INFORMATION_MESSAGE);
			// 2. puis ajout dans le modèle
			//leModeleVoitures.créé(laVoiture);
			return laVoiture;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune création effectuée dans la liste de voiture.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}

		return laVoiture;
	}

	public boolean modifier (String immatriculation, String marque, String modele, String carburant, boolean status) {

		// création d'une instance voiture pour abriter 
				// les données à modifier par le CRUD
		Voiture leVoiture = new Voiture(immatriculation, marque, modele, carburant, status);
		try {
//			Voiture ancienVoiture = crud.lire(immatriculation);
			// 1. sauvegarde d'abord dans la BD
//			leVoiture = crud.modifier(leVoiture);
			if (crud.modifier(leVoiture))
			{
				JOptionPane.showMessageDialog(
						null,
						"La voiture a été modifié.\n\n"
								, "",
						JOptionPane.INFORMATION_MESSAGE);
			}else {JOptionPane.showMessageDialog(
					null,
					"Cette voiture n'est pas dans la liste des voitures.\n\n"
							, "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
				
			}
//			notifications.firePropertyChange("Voiture", ancienVoiture, leVoiture);
			// 2. puis ajout dans le modèle --> MAJ du JTable auto

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune modification effectuée dans la liste de voitures.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	public void supprimer(String immatriculation) {

		try {
//			crud.supprimer(immatriculation);
			if (crud.supprimer(immatriculation))
			{
				JOptionPane.showMessageDialog(
						null,
						"La voiture a été supprimé de la liste des voitures.\n\n"
								, "",
						JOptionPane.INFORMATION_MESSAGE);
			}else {JOptionPane.showMessageDialog(
					null,
					"Cette voiture n'est pas dans la liste des voitures.\n\n"
							, "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
				
			}
			// suppression de la ligne dans le modèle
//			leModeleVoitures.supprimé(numeroLigne);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune suppression effectuée dans la listye de voitures.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public Voiture rechercher(String immatriculation) {
		Voiture maVoiture = new Voiture();
		

		try {
			maVoiture = crud.lire(immatriculation);
			if (maVoiture.getImmatriculation() != null)
			{
				return maVoiture;
			}else {JOptionPane.showMessageDialog(
					null,
					"Cette voiture n'est pas dans la liste des voitures.\n\n"
							, "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune recherche effectuée dans la liste de voitures.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
		return maVoiture;
	}
	
	public void louer(String immatriculation) {
		try {
			Voiture maVoiture = new Voiture();
			maVoiture=rechercher(immatriculation);
			if (maVoiture != null)
			{				
				crud.louer(immatriculation);
				leModeleVoitures.lu(maVoiture);
			}else {JOptionPane.showMessageDialog(
					null,
					"Il n'y a aucune voiture dans la liste des voitures.\n\n"
							, "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"problème lors de la location de la voiture.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void rentrer(String immatriculation) {
		try {
			Voiture maVoiture = new Voiture();
			maVoiture=rechercher(immatriculation);
			if (maVoiture != null)
			{				
				crud.rentrer(immatriculation);
				leModeleVoitures.lu(maVoiture);
			}else {JOptionPane.showMessageDialog(
					null,
					"Il n'y a aucune voiture dans la liste des voitures.\n\n"
							, "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"problème lors de la rentrée de la voiture.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void lister() {
		try {
			List<Voiture> listeVoitures = crud.lire();
			if (listeVoitures.size()!=0)
			{
				leModeleVoitures.luAll(listeVoitures);
			}else {JOptionPane.showMessageDialog(
					null,
					"Il n'y a aucune voiture dans la liste des voitures.\n\n"
							, "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"problème d'affichage de la liste de voitures.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public ArrayList<Voiture> rechercherMarque(String marque) {
		ArrayList<Voiture> listeVoituresMarque = new ArrayList();
		try {
			listeVoituresMarque = crud.rechercherMarque(marque);
			if (listeVoituresMarque.size() != 0)
			{
				leModeleVoitures.luAll(listeVoituresMarque);
				return listeVoituresMarque;
			}else {JOptionPane.showMessageDialog(
					null,
					"Il n'y a aucune voiture de cette marque disponible dans la liste des voitures.\n\n"
							, "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"problème d'affichage de la liste de voitures.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
		return listeVoituresMarque;
	}

//	public void rechercher(String text) {
//		try {
//			List<Voiture> lus = crud.chercher(text);
//			leModeleVoitures.lu(lus);
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(
//					null,
//					"Aucune recherche effectuée dans la BD.\n\n"
//							+ e.getMessage(), "Problème rencontré",
//					JOptionPane.ERROR_MESSAGE);
//		}
//	}

//	public void apercu() {
//		new SwingWorker<JasperPrint, Void>() {
//
//			@Override
//			protected JasperPrint doInBackground() throws Exception {
//				List<Voiture> voitures = crud.lire();
//				return JasperFacade.chargeEtcompile("Voitures.jrxml", voitures);
//			}
//
//			@Override
//			protected void done() {
//				try {
//					JasperPrint print = get();
//					JasperFacade.apercu(print);
//
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					JOptionPane.showMessageDialog(null, "Aucun aperçu.\n\n"
//							+ e.getCause().getMessage(), "Problème rencontré",
//							JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		}.execute();
//	}

//	public void apercuDirect() {
//		try {
//			JasperPrint print = JasperFacade.chargeEtcompile("Voitures.jrxml", crud.lire());
//			JasperFacade.apercu(print);
//		} catch (JRException e) {
//			JOptionPane.showMessageDialog(null, "Aucun aperçu.\n\n"
//					+ e.getMessage(), "Problème rencontré",
//					JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
//		}
//	}


//	public void imprimer() {
//		System.out.println(System.currentTimeMillis() + ": impression en préparation");
//		new SwingWorker<JasperPrint, Void>() {
//
//			@Override
//			protected JasperPrint doInBackground() throws Exception {
//				List<Voiture> données = crud.lire();
//				return JasperFacade.chargeEtcompile("Voiture.jrxml", données);
//			}
//
//			@Override
//			protected void done() {
//				try {
//					JasperPrint print = get();
//					JasperFacade.imprimer(print);
//
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					JOptionPane.showMessageDialog(null,
//							"Impression impossible.\n\n"
//									+ e.getCause().getMessage(),
//							"Problème rencontré", JOptionPane.ERROR_MESSAGE);
//				} catch (JRException e) {
//					JOptionPane.showMessageDialog(null,
//							"Impression impossible.\n\n" + e.getMessage(),
//							"Problème rencontré", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		}.execute();
//	}

//	public void export(Window parent) {
//		System.out.println(System.currentTimeMillis() + ": export en préparation");
//		new SwingWorker<List<Voiture>, Void>() {
//
//			@Override
//			protected List<Voiture> doInBackground() throws Exception {
//				return crud.lire();
//			}
//
//			@Override
//			protected void done() {
//				try {
//					List<Voiture> elements = get();
//					FExport laFenetre = new FExport(parent,	"Voitures", "Voiture.jrxml", elements);
//					laFenetre.setModal(true);
//					laFenetre.setLocationRelativeTo(null);
//					laFenetre.setVisible(true);
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					JOptionPane.showMessageDialog(null,
//							"Lecture impossible pour export.\n\n"
//									+ e.getCause().getMessage(),
//							"Problème rencontré", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		}.execute();
//	}
}
