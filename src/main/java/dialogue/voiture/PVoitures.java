package dialogue.voiture;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.Instant;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;
import controle.ControleVoiture;
import controle.connection.Connexion;
import controle.modele.ModeleVoitures;
import controle.utilitaires.GestionDates;
import dialogue.UI;
import dialogue.rendu.BooleenRenderer;
import dialogue.rendu.GrasRenderer;

import entite.Voiture;

public class PVoitures extends JPanel implements TableModelListener {

	private static final long serialVersionUID = 1L;


	// propriétés non graphiques
	// -------------------------
	private ControleVoiture controleVoiture;
	private Connexion connexion;

	private JTable table;
	private JTextField txtImmatriculation;
	private JTextField txtMarque;
	private JTextField txtModele;
	private JTextField txtCarburant;
	private JTextField txtStatus;

	private JTextArea textArea;

	private JCheckBox locationEnCours;

	private final Action actionAnnuler = new ActionAnnuler();
	private final Action actionAjouter = new ActionAjouter();
	private final Action actionRechercher = new ActionRechercher();
	private final Action actionModifier = new ActionModifier();
	private final Action actionSupprimer = new ActionSupprimer();

	private final Action actionLister = new ActionLister();
	private final Action ActionLouer = new ActionLouer();
	private final Action actionRentrer = new ActionRentrer();

	private final Action actionAccueil = new ActionAccueil();


	private JDialog fenetre;


	private JButton btnModifier;


	private JButton btnRechercher;

	private JButton btnLister;
	
	private JButton btnAjouter;
	
	private JButton btnLouer;
	
	private JButton btnSupprimer;
	
	private JButton btnRentrer;
	
	public PVoitures setConnexion(Connexion connexion) {
		this.connexion = connexion;
		controleVoiture = new ControleVoiture(connexion);
		return this;
	}

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 */
	public PVoitures() {
		setBackground(new Color(0x33, 0xB5, 0xE5));
		setBorder(null);
		setLayout(new BorderLayout(0, 0));

		JPanel panel_menu = new JPanel();
		panel_menu.setBackground(new Color(0x00, 0x99, 0xCC));
		add(panel_menu, BorderLayout.WEST);
		panel_menu.setBorder(new CompoundBorder(null, new EmptyBorder(5, 5, 5,
				5)));
		GridBagLayout gbl_panel_menu = new GridBagLayout();
		gbl_panel_menu.columnWidths = new int[] { 0, 0 };
		gbl_panel_menu.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_menu.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_menu.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_menu.setLayout(gbl_panel_menu);

		JLabel lblTitre = new JLabel("Voitures");
		lblTitre.setIcon(new ImageIcon(PVoitures.class
				.getResource("/images/gestion/client/People-64-actif.png")));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		btnRechercher = new JButton("Rechercher");
		btnRechercher.setAction(actionRechercher);
		btnRechercher.setForeground(Color.BLACK);
		btnRechercher.setHorizontalAlignment(SwingConstants.LEFT);
		btnRechercher.setIcon(new ImageIcon(PVoitures.class
				.getResource("/images/gestion/Search-48.png")));
		GridBagConstraints gbc_btnRechercher = new GridBagConstraints();
		gbc_btnRechercher.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRechercher.anchor = GridBagConstraints.LINE_START;
		gbc_btnRechercher.insets = new Insets(0, 0, 5, 0);
		gbc_btnRechercher.gridx = 0;
		gbc_btnRechercher.gridy = 1;
		panel_menu.add(btnRechercher, gbc_btnRechercher);
		UI.deshabillerBouton(btnRechercher, "Search");
		
		btnAjouter = new JButton("Ajouter");
		btnAjouter.setAction(actionAjouter);
		btnAjouter.setForeground(Color.BLACK);
		btnAjouter.setHorizontalAlignment(SwingConstants.LEFT);
		btnAjouter.setIcon(new ImageIcon(PVoitures.class
				.getResource("/images/gestion/Add-New-48.png")));
		GridBagConstraints gbc_btnAjouter = new GridBagConstraints();
		gbc_btnAjouter.anchor = GridBagConstraints.LINE_START;
		gbc_btnAjouter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAjouter.insets = new Insets(0, 0, 5, 0);
		gbc_btnAjouter.gridx = 0;
		gbc_btnAjouter.gridy = 2;
		panel_menu.add(btnAjouter, gbc_btnAjouter);
		UI.deshabillerBouton(btnAjouter, "Add-New");
		
		btnModifier = new JButton("Modifier");
		btnModifier.setAction(actionModifier);
		btnModifier.setForeground(Color.BLACK);
		btnModifier.setHorizontalAlignment(SwingConstants.LEFT);
		btnModifier.setIcon(new ImageIcon(PVoitures.class
				.getResource("/images/gestion/Data-Edit-48.png")));
		GridBagConstraints gbc_btnModifier = new GridBagConstraints();
		gbc_btnModifier.anchor = GridBagConstraints.LINE_START;
		gbc_btnModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModifier.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifier.gridx = 0;
		gbc_btnModifier.gridy = 3;
		panel_menu.add(btnModifier, gbc_btnModifier);
		UI.deshabillerBouton(btnModifier, "Data-Edit");

		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setAction(actionSupprimer);
		btnSupprimer.setForeground(Color.BLACK);
		btnSupprimer.setHorizontalAlignment(SwingConstants.LEFT);
		btnSupprimer.setIcon(new ImageIcon(PVoitures.class
				.getResource("/images/gestion/Garbage-Open-48.png")));
		GridBagConstraints gbc_btnSupprimer = new GridBagConstraints();
		gbc_btnSupprimer.anchor = GridBagConstraints.LINE_START;
		gbc_btnSupprimer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSupprimer.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupprimer.gridx = 0;
		gbc_btnSupprimer.gridy = 4;
		panel_menu.add(btnSupprimer, gbc_btnSupprimer);
		UI.deshabillerBouton(btnSupprimer, "Garbage-Open");

		btnLister = new JButton("Lister");
		btnLister.setAction(actionLister);
		btnLister.setForeground(Color.BLACK);
		btnLister.setHorizontalAlignment(SwingConstants.LEFT);
		btnLister.setIcon(new ImageIcon(PVoitures.class
				.getResource("/images/gestion/Preview-48.png")));
		GridBagConstraints gbc_btnLister = new GridBagConstraints();
		gbc_btnLister.weighty = 1.0;
		gbc_btnLister.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLister.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnLister.insets = new Insets(0, 0, 5, 0);
		gbc_btnLister.gridx = 0;
		gbc_btnLister.gridy = 5;
		panel_menu.add(btnLister, gbc_btnLister);
		UI.deshabillerBouton(btnLister, "Preview");

		btnLouer = new JButton("louer");
		btnLouer.setAction(ActionLouer);
		btnLouer.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnlouer = new GridBagConstraints();
		gbc_btnlouer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnlouer.anchor = GridBagConstraints.LINE_START;
		gbc_btnlouer.insets = new Insets(0, 0, 5, 0);
		gbc_btnlouer.gridx = 0;
		gbc_btnlouer.gridy = 6;
		panel_menu.add(btnLouer, gbc_btnlouer);
		UI.deshabillerBouton(btnLouer, "Printer");

		btnRentrer = new JButton("Rentrer");
		btnRentrer.setAction(actionRentrer);
		btnRentrer.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnRentrer = new GridBagConstraints();
		gbc_btnRentrer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRentrer.anchor = GridBagConstraints.LINE_START;
		gbc_btnRentrer.insets = new Insets(0, 0, 5, 0);
		gbc_btnRentrer.gridx = 0;
		gbc_btnRentrer.gridy = 7;
		panel_menu.add(btnRentrer, gbc_btnRentrer);
		UI.deshabillerBouton(btnRentrer, "Data-Export");

		JButton btnAccueil = new JButton("Accueil");
		btnAccueil.setAction(actionAccueil);
		btnAccueil.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnAccueil = new GridBagConstraints();
		gbc_btnAccueil.weighty = 1.0;
		gbc_btnAccueil.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnAccueil.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccueil.gridx = 0;
		gbc_btnAccueil.gridy = 8;
		panel_menu.add(btnAccueil, gbc_btnAccueil);
		UI.deshabillerBouton(btnAccueil, "Home");

		JPanel panel_principal = new JPanel();
		panel_principal.setBackground(new Color(197, 234, 248));
		add(panel_principal, BorderLayout.CENTER);
		panel_principal.setLayout(new MigLayout("", "[][10%][10%][grow]",
				"[grow][42%,grow][]"));

		JPanel panel_formulaire = new JPanel();
		panel_formulaire.setOpaque(false);
		panel_formulaire.setBorder(new CompoundBorder(new LineBorder(new Color(
				0, 0, 0), 2, true), new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_formulaire, "cell 0 0 4 1,grow");
		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 79, 182, 91, 182, 119 };
		gbl_panel_formulaire.rowHeights = new int[] { 26, 26, 26, 26, 0, 75 };
		gbl_panel_formulaire.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				0.0 };
		gbl_panel_formulaire.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0 };
		panel_formulaire.setLayout(gbl_panel_formulaire);

		JLabel lblCode = new JLabel("Immatriculation");
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.anchor = GridBagConstraints.EAST;
		gbc_lblCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblCode.gridx = 0;
		gbc_lblCode.gridy = 0;
		panel_formulaire.add(lblCode, gbc_lblCode);
		UI.habiller(lblCode);

		txtImmatriculation = new JTextField();
		lblCode.setLabelFor(txtImmatriculation);
		txtImmatriculation.setEditable(false);
		GridBagConstraints gbc_txtImmatriculation = new GridBagConstraints();
		gbc_txtImmatriculation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtImmatriculation.insets = new Insets(0, 0, 5, 5);
		gbc_txtImmatriculation.gridx = 1;
		gbc_txtImmatriculation.gridy = 0;
		panel_formulaire.add(txtImmatriculation, gbc_txtImmatriculation);
		txtImmatriculation.setColumns(10);
		UI.habiller(txtImmatriculation);

		locationEnCours = new JCheckBox("Location en cours");
		locationEnCours.setOpaque(false);
		GridBagConstraints gbc_chckbxCartefidelite = new GridBagConstraints();
		gbc_chckbxCartefidelite.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxCartefidelite.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxCartefidelite.gridx = 4;
		gbc_chckbxCartefidelite.gridy = 0;
		panel_formulaire.add(locationEnCours, gbc_chckbxCartefidelite);
		UI.habiller(locationEnCours);

		JLabel lblMarque = new JLabel("Marque");
		GridBagConstraints gbc_lblMarque = new GridBagConstraints();
		gbc_lblMarque.anchor = GridBagConstraints.EAST;
		gbc_lblMarque.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarque.gridx = 0;
		gbc_lblMarque.gridy = 1;
		panel_formulaire.add(lblMarque, gbc_lblMarque);
		UI.habiller(lblMarque);

		txtMarque = new JTextField();
		lblMarque.setLabelFor(txtMarque);
		txtMarque.setEditable(false);
		GridBagConstraints gbc_txtMarque = new GridBagConstraints();
		gbc_txtMarque.anchor = GridBagConstraints.NORTH;
		gbc_txtMarque.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMarque.insets = new Insets(0, 0, 5, 5);
		gbc_txtMarque.gridx = 1;
		gbc_txtMarque.gridy = 1;
		panel_formulaire.add(txtMarque, gbc_txtMarque);
		txtMarque.setColumns(10);
		UI.habiller(txtMarque);

		JLabel lblModele = new JLabel("Modele");
		GridBagConstraints gbc_lblModele = new GridBagConstraints();
		gbc_lblModele.anchor = GridBagConstraints.EAST;
		gbc_lblModele.insets = new Insets(0, 0, 5, 5);
		gbc_lblModele.gridx = 2;
		gbc_lblModele.gridy = 1;
		panel_formulaire.add(lblModele, gbc_lblModele);
		UI.habiller(lblModele);

		txtModele = new JTextField();
		lblModele.setLabelFor(txtModele);
		txtModele.setEditable(false);
		GridBagConstraints gbc_txtModele = new GridBagConstraints();
		gbc_txtModele.anchor = GridBagConstraints.NORTH;
		gbc_txtModele.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtModele.insets = new Insets(0, 0, 5, 0);
		gbc_txtModele.gridwidth = 2;
		gbc_txtModele.gridx = 3;
		gbc_txtModele.gridy = 1;
		panel_formulaire.add(txtModele, gbc_txtModele);
		txtModele.setColumns(10);
		UI.habiller(txtModele);

		JLabel lblCarburant = new JLabel("Carburant");
		GridBagConstraints gbc_lblCarburant = new GridBagConstraints();
		gbc_lblCarburant.anchor = GridBagConstraints.EAST;
		gbc_lblCarburant.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarburant.gridx = 0;
		gbc_lblCarburant.gridy = 2;
		panel_formulaire.add(lblCarburant, gbc_lblCarburant);
		UI.habiller(lblCarburant);

		txtCarburant = new JTextField();
		lblCarburant.setLabelFor(txtCarburant);
		txtCarburant.setEditable(false);
		GridBagConstraints gbc_txtCarburant = new GridBagConstraints();
		gbc_txtCarburant.anchor = GridBagConstraints.NORTH;
		gbc_txtCarburant.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCarburant.insets = new Insets(0, 0, 5, 5);
		gbc_txtCarburant.gridwidth = 4;
		gbc_txtCarburant.gridx = 1;
		gbc_txtCarburant.gridy = 2;
		panel_formulaire.add(txtCarburant, gbc_txtCarburant);
		txtCarburant.setColumns(10);
		UI.habiller(txtCarburant);

		JLabel lblStatus = new JLabel("Status");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.EAST;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 3;
		panel_formulaire.add(lblStatus, gbc_lblStatus);
		UI.habiller(lblStatus);

		txtStatus = new JTextField();
		lblStatus.setLabelFor(txtStatus);
		txtStatus.setEditable(false);
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.anchor = GridBagConstraints.NORTH;
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.insets = new Insets(0, 0, 5, 5);
		gbc_txtStatus.gridx = 1;
		gbc_txtStatus.gridy = 3;
		panel_formulaire.add(txtStatus, gbc_txtStatus);
		txtStatus.setColumns(10);
		UI.habiller(txtStatus);

		JLabel lblRemarques = new JLabel("Remarques");
		lblRemarques.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblRemarques = new GridBagConstraints();
		gbc_lblRemarques.anchor = GridBagConstraints.NORTH;
		gbc_lblRemarques.insets = new Insets(0, 0, 5, 5);
		gbc_lblRemarques.gridx = 0;
		gbc_lblRemarques.gridy = 5;
		panel_formulaire.add(lblRemarques, gbc_lblRemarques);
		UI.habiller(lblRemarques);

		textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		lblRemarques.setLabelFor(textArea);
		Border cadre = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		textArea.setBorder(cadre);
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridwidth = 4;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 5;
		panel_formulaire.add(textArea, gbc_textArea);
		UI.habiller(textArea);

		SwingUtilities.invokeLater(
				() -> btnAccueil.requestFocusInWindow()		
		);
	}

	private ImageIcon getImage(String image) {
		return new ImageIcon(getClass().getResource(image));
	}
	


	/* ************************************************************
	 * Gestion des événements concernant le modèle en redéfinissant la méthode
	 * tableChanged() de l'interface TableModelListener
	 * ***********************************************************
	 */
	public void tableChanged(TableModelEvent unEvenement) {
		switch (unEvenement.getType()) {
		case TableModelEvent.INSERT:
			System.out.println("La table a été mise à jour, "
					+ "il y a eu une insertion !!!");
			break;
		case TableModelEvent.DELETE:
			System.out
					.println("La table a été mise à jour, "
							+ "il y a eu une suppression !!!");
			break;
		case TableModelEvent.UPDATE:
			System.out
					.println("La table a été mise à jour,"
							+ " il y a eu une modification !!!");
			break;
		default:
			break;
		}
	}

	private class ActionAccueil extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionAccueil() {
			putValue(NAME, "Accueil");
			putValue(SHORT_DESCRIPTION, "Retourner sur l'écran d'accueil");
		}

		public void actionPerformed(ActionEvent e) {
			getFenetre().dispose();
		}
	}

	private class ActionAnnuler extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionAnnuler() {
			putValue(NAME, "Annuler");
			putValue(SHORT_DESCRIPTION, "Annuler l'action en cours");
		}

		public void actionPerformed(ActionEvent e) {
			changerPanneau(PVoitures.this, "Gestion des Voitures");
		}
	}

	private class ActionExport extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionExport() {
			putValue(NAME, "Export");
			putValue(SHORT_DESCRIPTION,
					"Exporter les Voitures sous forme de fichier");
		}

		public void actionPerformed(ActionEvent e) {
			//controleVoiture.export(getFenetre());
		}
	}

	private class ActionLister extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionLister() {
			putValue(NAME, "Lister");
			putValue(SHORT_DESCRIPTION, "Liste des voitures");
		}

		public void actionPerformed(ActionEvent e) {
			PVoitureLister liste = new PVoitureLister();
			liste.setActionAnnuler(actionAnnuler);
//			liste.setActionExport(actionExport);
			ControleVoiture controle = new ControleVoiture(connexion);
			liste.setControleVoiture(controle);
			btnLister.setIcon(getImage("/images/gestion/Search-48.png"));
			changerPanneau(liste, "Liste des Voiture(s)");
		}
	}

	

	private class ActionSupprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionSupprimer() {
			putValue(NAME, "Supprimer");
			putValue(SHORT_DESCRIPTION, "Supprimer la Voiture sélectionné");
		}

		public void actionPerformed(ActionEvent e) {
			PVoitureSupprimer supprimer = new PVoitureSupprimer();
			supprimer.setActionAnnuler(actionAnnuler);
//			supprimer.setActionExport(actionExport);
			ControleVoiture controle = new ControleVoiture(connexion);
			supprimer.setControleVoiture(controle);

			btnSupprimer.setIcon(getImage("/images/gestion/Search-48.png"));
			changerPanneau(supprimer, "Recherche de Voiture(s)");
		}
	}

	private class ActionModifier extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionModifier() {
			putValue(NAME, "Modifier");
			putValue(SHORT_DESCRIPTION, "Modifier la Voiture sélectionnée");
		}

		public void actionPerformed(ActionEvent e) {
			PVoitureModifier modification = new PVoitureModifier();
			modification.setActionAnnuler(actionAnnuler);
//			modification.setActionExport(actionExport);
			ControleVoiture controle = new ControleVoiture(connexion);
			modification.setControleVoiture(controle);

			btnModifier.setIcon(getImage("/images/gestion/Search-48.png"));
			changerPanneau(modification, "Recherche de Voiture(s)");
		}
	}
	
	private class ActionRechercher extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionRechercher() {
			putValue(NAME, "Rechercher");
			putValue(SHORT_DESCRIPTION, "Rechercher parmi les Voitures");
		}

		public void actionPerformed(ActionEvent e) {

			// --- RECHERCHE EN MODE FICHE ---
			// création de la fenêtre
			PVoitureRecherche recherche = new PVoitureRecherche();
			recherche.setActionAnnuler(actionAnnuler);
//			recherche.setActionExport(actionExport);
			ControleVoiture controle = new ControleVoiture(connexion);
			recherche.setControleVoiture(controle);

			btnRechercher.setIcon(getImage("/images/gestion/Search-48.png"));
			changerPanneau(recherche, "Recherche de Voiture(s)");
		}
	}
	
	private class ActionLouer extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionLouer() {
			putValue(NAME, "louer");
			putValue(SHORT_DESCRIPTION, "louer une voiture");
		}

		public void actionPerformed(ActionEvent e) {
			//controleVoiture.louer();
			PVoitureLouer location = new PVoitureLouer();
			location.setActionAnnuler(actionAnnuler);
//			location.setActionExport(actionExport);
			ControleVoiture controle = new ControleVoiture(connexion);
			location.setControleVoiture(controle);
			
			btnLouer.setIcon(getImage("/images/gestion/Add-New-48.png"));
			changerPanneau(location ,"louer une nouvelle voiture");
			
		}
	}
	
	private class ActionRentrer extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionRentrer() {
			putValue(NAME, "Rentrer");
			putValue(SHORT_DESCRIPTION, "Rentrer une voiture");
		}

		public void actionPerformed(ActionEvent e) {
			//controleVoiture.louer();
			PVoitureRentrer location = new PVoitureRentrer();
			location.setActionAnnuler(actionAnnuler);
//			location.setActionExport(actionExport);
			ControleVoiture controle = new ControleVoiture(connexion);
			location.setControleVoiture(controle);
			
			btnLouer.setIcon(getImage("/images/gestion/Add-New-48.png"));
			changerPanneau(location ,"louer une nouvelle voiture");
			
		}
	}
	
	private class ActionAjouter extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionAjouter() {
			putValue(NAME, "Ajouter");
			putValue(SHORT_DESCRIPTION, "Ajouter une nouvelle voiture");
		}

		public void actionPerformed(ActionEvent e) {
			// --- AJOUT EN MODE FICHE ---
			PVoitureAjout ajout = new PVoitureAjout();
			ajout.setActionAnnuler(actionAnnuler);
//			ajout.setActionExport(actionExport);
			ajout.setControleVoiture(controleVoiture);
			
			btnAjouter.setIcon(getImage("/images/gestion/Add-New-48.png"));
			changerPanneau(ajout ,"Ajouter une nouvelle voiture");
		}
	}

	public void setFenetre(JDialog fenetre) {
		this.fenetre = fenetre;
	}
	
	private JDialog getFenetre() {
//		Window window = SwingUtilities.getWindowAncestor(this);
		return fenetre;
	}
	
	private void changerPanneau(JPanel panneau, String titre) {
		Window fenetre = getFenetre();
		if (fenetre instanceof JDialog) {
			JDialog dialogue = (JDialog) fenetre;
			dialogue.setContentPane(panneau);
			dialogue.setTitle(titre);
		}
		fenetre.revalidate();
	}
}
