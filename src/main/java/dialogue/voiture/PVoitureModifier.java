package dialogue.voiture;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

import net.miginfocom.swing.MigLayout;
import controle.ControleVoiture;
import controle.utilitaires.GestionDates;
import dialogue.UI;
import dialogue.rendu.BooleenRenderer;
import dialogue.rendu.GrasRenderer;
import entite.Voiture;

//public class PVoitureRecherche extends JPanel implements TableModelListener {
public class PVoitureModifier extends JPanel {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------
	private ControleVoiture controleVoiture;

	private JTable table;
	private JTextField txtImmatriculation;
	private JTextField txtMarque;
	private JTextField txtModele;
	private JTextField txtCarburant;
	private JTextField txtStatus;

	private JTextArea textArea;

	private JCheckBox locationEnCours;

	private final Action actionPrincipale = new ActionPrincipale();
	private final Action actionModifier = new ActionModifier();

	private final Action actionApercu = new ActionApercu();
	private final Action actionImprimer = new ActionImprimer();

	private JButton btnAction;
	private JButton btnModifier;

	private JLabel lblTitre;

	private JComboBox<String> comboBoxTrie;

	private JButton btnAccueil;

	private JButton btnExport;

	PVoitureModifier setControleVoiture(ControleVoiture controleVoiture) {
		this.controleVoiture = controleVoiture;

		return this;
	}

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 * @wbp.parser.constructor
	 */
	PVoitureModifier() {
		createContents();
	}

	private void createContents() {
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

		lblTitre = new JLabel("Modifier");
		lblTitre.setIcon(new ImageIcon(PVoitureModifier.class.getResource("/images/gestion/client/User-Find-64.png")));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.LINE_START;
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 25));

		btnAction = new JButton("Rechercher");
		btnAction.setAction(actionPrincipale);
		btnAction.setForeground(Color.BLACK);
		btnAction.setIcon(new ImageIcon(PVoitureModifier.class.getResource("/images/gestion/Search-48.png")));
		UI.deshabillerBouton(btnAction, "Search");
		btnAction.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnAction = new GridBagConstraints();
		gbc_btnAction.anchor = GridBagConstraints.LINE_START;
		gbc_btnAction.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAction.insets = new Insets(0, 0, 5, 0);
		gbc_btnAction.gridx = 0;
		gbc_btnAction.gridy = 1;
		panel_menu.add(btnAction, gbc_btnAction);
		
		btnModifier = new JButton("Modifier");
		btnModifier.setAction(actionModifier);
		btnModifier.setForeground(Color.BLACK);
		btnModifier.setIcon(new ImageIcon(PVoitureModifier.class.getResource("/images/gestion/Search-48.png")));
		UI.deshabillerBouton(btnModifier, "Search");
		btnModifier.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnModifier = new GridBagConstraints();
		gbc_btnModifier.anchor = GridBagConstraints.LINE_START;
		gbc_btnModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModifier.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifier.gridx = 0;
		gbc_btnModifier.gridy = 2;
		panel_menu.add(btnModifier, gbc_btnModifier);

		JButton btnApercu = new JButton("Apercu");
		btnApercu.setAction(actionApercu);
		btnApercu.setForeground(Color.BLACK);
		btnApercu.setHorizontalAlignment(SwingConstants.LEFT);
		btnApercu.setIcon(new ImageIcon(PVoitureModifier.class
				.getResource("/images/gestion/Preview-48.png")));
		GridBagConstraints gbc_btnApercu = new GridBagConstraints();
		gbc_btnApercu.weighty = 1.0;
		gbc_btnApercu.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApercu.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnApercu.insets = new Insets(0, 0, 5, 0);
		gbc_btnApercu.gridx = 0;
		gbc_btnApercu.gridy = 5;
		panel_menu.add(btnApercu, gbc_btnApercu);
		UI.deshabillerBouton(btnApercu, "Preview");

		JButton btnImprimer = new JButton("Imprimer");
		btnImprimer.setAction(actionImprimer);
		btnImprimer.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnImprimer = new GridBagConstraints();
		gbc_btnImprimer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImprimer.anchor = GridBagConstraints.LINE_START;
		gbc_btnImprimer.insets = new Insets(0, 0, 5, 0);
		gbc_btnImprimer.gridx = 0;
		gbc_btnImprimer.gridy = 6;
		panel_menu.add(btnImprimer, gbc_btnImprimer);
		UI.deshabillerBouton(btnImprimer, "Printer");

		btnExport = new JButton("Export");
		btnExport.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnExport = new GridBagConstraints();
		gbc_btnExport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExport.anchor = GridBagConstraints.LINE_START;
		gbc_btnExport.insets = new Insets(0, 0, 5, 0);
		gbc_btnExport.gridx = 0;
		gbc_btnExport.gridy = 7;
		panel_menu.add(btnExport, gbc_btnExport);
		UI.deshabillerBouton(btnExport, "Data-Export");

		btnAccueil = new JButton("Annuler");
		btnAccueil.setIcon(new ImageIcon(PVoitureModifier.class.getResource("/images/gestion/Cancel-48.png")));
		btnAccueil.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnAccueil = new GridBagConstraints();
		gbc_btnAccueil.weighty = 1.0;
		gbc_btnAccueil.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnAccueil.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccueil.gridx = 0;
		gbc_btnAccueil.gridy = 8;
		panel_menu.add(btnAccueil, gbc_btnAccueil);
		UI.deshabillerBouton(btnAccueil, "Cancel");

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

		JLabel lblImmatriculation = new JLabel("Immatriculation");
		GridBagConstraints gbc_lblImmatriculation = new GridBagConstraints();
		gbc_lblImmatriculation.anchor = GridBagConstraints.EAST;
		gbc_lblImmatriculation.insets = new Insets(0, 0, 5, 5);
		gbc_lblImmatriculation.gridx = 0;
		gbc_lblImmatriculation.gridy = 0;
		panel_formulaire.add(lblImmatriculation, gbc_lblImmatriculation);
		UI.habiller(lblImmatriculation);

		txtImmatriculation = new JTextField();
		lblImmatriculation.setLabelFor(txtImmatriculation);
		GridBagConstraints gbc_txtImmatriculation = new GridBagConstraints();
		gbc_txtImmatriculation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtImmatriculation.insets = new Insets(0, 0, 5, 5);
		gbc_txtImmatriculation.gridx = 1;
		gbc_txtImmatriculation.gridy = 0;
		panel_formulaire.add(txtImmatriculation, gbc_txtImmatriculation);
		txtImmatriculation.setColumns(10);
		UI.habiller(txtImmatriculation);


//		locationEnCours = new JCheckBox("Location en cours");
//		locationEnCours.setOpaque(false);
//		GridBagConstraints gbc_chckbxCartefidelite = new GridBagConstraints();
//		gbc_chckbxCartefidelite.anchor = GridBagConstraints.NORTHWEST;
//		gbc_chckbxCartefidelite.insets = new Insets(0, 0, 5, 0);
//		gbc_chckbxCartefidelite.gridx = 4;
//		gbc_chckbxCartefidelite.gridy = 0;
//		panel_formulaire.add(locationEnCours, gbc_chckbxCartefidelite);
//		UI.habiller(locationEnCours);

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
//		txtMarque.setEditable(false);
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
//		txtModele.setEditable(false);
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
//		txtCarburant.setEditable(false);
		GridBagConstraints gbc_txtCarburant = new GridBagConstraints();
		gbc_txtCarburant.anchor = GridBagConstraints.NORTH;
		gbc_txtCarburant.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCarburant.insets = new Insets(0, 0, 5, 0);
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
		gbc_lblStatus.gridy = 4;
		panel_formulaire.add(lblStatus, gbc_lblStatus);
		UI.habiller(lblStatus);

		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		lblStatus.setLabelFor(txtStatus);
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.anchor = GridBagConstraints.NORTH;
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.insets = new Insets(0, 0, 5, 0);
		gbc_txtStatus.gridwidth = 4;
		gbc_txtStatus.gridx = 1;
		gbc_txtStatus.gridy = 4;
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
//		textArea.setEditable(false);
		Border cadre = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		textArea.setBorder(cadre);
		textArea.setBackground(SystemColor.control);
		lblRemarques.setLabelFor(textArea);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridwidth = 4;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 5;
		panel_formulaire.add(textArea, gbc_textArea);
		UI.habiller(textArea);

//		table = new JTable();
//		UI.habiller(table);
//
//		JScrollPane scrollPane = new JScrollPane(table);
//		panel_principal.add(scrollPane, "cell 0 1 4 1,grow");
//
//		JLabel lblTri = new JLabel("Trier la liste par");
//		lblTri.setIcon(new ImageIcon(PVoitureRecherche.class
//				.getResource("/images/gestion/Sort-Ascending-32.png")));
//		panel_principal.add(lblTri, "cell 1 2,alignx trailing");
//		UI.habiller(lblTri);
//
//		comboBoxTrie = new JComboBox<>();
//		UI.habiller(comboBoxTrie);
//		panel_principal.add(comboBoxTrie, "cell 2 2,growx");
		
		SwingUtilities.invokeLater(
				() -> btnAccueil.requestFocusInWindow()		
		);
	}

	/* ************************************************************
	 * Gestion des événements concernant le modèle en redéfinissant la méthode
	 * tableChanged() de l'interface TableModelListener
	 * ***********************************************************
	 */
//	public void tableChanged(TableModelEvent unEvenement) {
//		switch (unEvenement.getType()) {
//		case TableModelEvent.INSERT:
//			System.out.println("La table a été mise à jour, "
//					+ "il y a eu une insertion !!!"
//					+ "\nFaisons d'autres actions suite à cet événement.");
//			break;
//		case TableModelEvent.DELETE:
//			System.out
//					.println("La table a été mise à jour, il y a eu une suppression !!!"
//							+ "\nFaisons d'autres actions suite à cet événement.");
//			break;
//		case TableModelEvent.UPDATE:
//			System.out
//					.println("La table a été mise à jour, il y a eu une modification !!!"
//							+ "\nFaisons d'autres actions suite à cet événement.");
//			break;
//		default:
//			break;
//		}
//	}

	private class ActionApercu extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionApercu() {
			putValue(NAME, "Aperçu");
			putValue(SHORT_DESCRIPTION, "Aperçu avant impression");
		}

		public void actionPerformed(ActionEvent e) {
			//controleVoiture.apercu();
		}
	}

	private class ActionImprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionImprimer() {
			putValue(NAME, "Imprimer");
			putValue(SHORT_DESCRIPTION, "Imprimer les données des clients");
		}

		public void actionPerformed(ActionEvent e) {
			//controleVoiture.imprimer();
		}
	}

	private class ActionPrincipale extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionPrincipale() {
			putValue(NAME, "Rechercher");
			putValue(SHORT_DESCRIPTION, "Rechercher parmi les voitures existantes");
		}

		public void actionPerformed(ActionEvent e) {
			txtImmatriculation.setEditable(true);
			String immatriculation = txtImmatriculation.getText();
			
			if (!immatriculation.equals("") ) {	
				Voiture maVoiture = controleVoiture.rechercher(immatriculation);
				if (maVoiture.getImmatriculation() != null)
				{
					txtImmatriculation.setText(maVoiture.getImmatriculation());
					txtModele.setText(maVoiture.getModele());
					txtMarque.setText(maVoiture.getMarque());
					txtCarburant.setText(maVoiture.getCarburant());
					if (maVoiture.isStatus())
					{
						txtStatus.setText("Louée");
					}else {
						txtStatus.setText("Disponible");
					}
					txtImmatriculation.setEditable(false);
				}else {
					txtImmatriculation.setText("");
					txtModele.setText("");
					txtMarque.setText("");
					txtCarburant.setText("");
					txtStatus.setText("");
				}
				txtImmatriculation.requestFocus();
			} else {
				JOptionPane.showMessageDialog(null, "l'immatriculation n'a pas été saisie "
						 , "Vérifiez votre saisie",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	private class ActionModifier extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionModifier() {
			putValue(NAME, "Modifier");
			putValue(SHORT_DESCRIPTION, "Modifier la voiture");
		}

		public void actionPerformed(ActionEvent e) {
			
			String immatriculation = txtImmatriculation.getText();
			String modele = txtModele.getText();
			String marque = txtMarque.getText();
			String carburant = txtCarburant.getText();
			System.out.println(immatriculation);System.out.println(modele);System.out.println(marque);System.out.println(carburant);
			
			if (!immatriculation.equals("") && !marque.equals("") && !modele.equals("") && !carburant.equals("")) {	
				Voiture maVoiture = controleVoiture.rechercher(immatriculation);
				if (maVoiture.getImmatriculation() != null)
				{
					txtImmatriculation.setText(immatriculation);
					txtModele.setText(modele);
					txtMarque.setText(marque);
					txtCarburant.setText(carburant);
					controleVoiture.modifier(immatriculation,marque,modele,carburant,maVoiture.isStatus());		
					
				}else {
					txtImmatriculation.setText("");
					txtModele.setText("");
					txtMarque.setText("");
					txtCarburant.setText("");
					txtStatus.setText("");
				}
				txtImmatriculation.requestFocus();
			} else {
				JOptionPane.showMessageDialog(null, "l'immatriculation n'a pas été saisie "
						 , "Vérifiez votre saisie",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}	
	
	void setActionAnnuler(Action action) {
		btnAccueil.setAction(action);
		UI.deshabillerBouton(btnAccueil, "Cancel");
	}

	void setActionExport(Action action) {
		btnExport.setAction(action);
		UI.deshabillerBouton(btnExport, "Data-Export");
	}
}
