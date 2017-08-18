package dialogue.voiture;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.time.Instant;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import controle.ControleVoiture;
import controle.utilitaires.GestionDates;
import dialogue.UI;
import dialogue.voiture.PVoitureAjout;
import entite.Voiture;

public class PVoitureLouerImmatriculation extends JPanel {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------
	private ControleVoiture controleVoiture;

	// propriétés graphiques
	// -------------------------
	private JTextField txtImmatriculation;
	private JTextField txtMarque;
	private JTextField txtModele;
	private JTextField txtCarburant;
	private JTextField txtStatus;

	private JTextArea textArea;

	private JCheckBox locationEnCours;

	private final Action actionPrincipale = new ActionPrincipale();

	private final Action actionApercu = new ActionApercu();
	private final Action actionImprimer = new ActionImprimer();

	private JLabel lblTitre;
	private JTextField txtCodepostal;
	private JTextField txtVille;

	private JButton btnAccueil;

	private JButton btnExport;

	PVoitureLouerImmatriculation setControleVoiture(ControleVoiture controleVoiture) {
		this.controleVoiture = controleVoiture;
		return this;
	}

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 * @wbp.parser.constructor
	 */
	PVoitureLouerImmatriculation() {
		this(null);
	}

	PVoitureLouerImmatriculation(Voiture voiture) {

		JFormattedTextField.AbstractFormatter formatter = new Formateur();

		createContents();
		if (voiture != null) {
			txtImmatriculation.setText(voiture.getImmatriculation());
			txtImmatriculation.setEditable(false);
			txtMarque.setText(voiture.getMarque());
			txtModele.setText(voiture.getModele());
			txtCarburant.setText(voiture.getCarburant());
			if (voiture.isStatus()){
				txtStatus.setText("Louée");
		    }else {
		    	txtStatus.setText("Disponible");
		    }
			txtStatus.setEditable(false);
//			locationEnCours.setSelected(voiture.isStatus());
		}
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

		lblTitre = new JLabel(getTitre());
		lblTitre.setIcon(new ImageIcon(PVoitureAjout.class.getResource(getIcone())));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.LINE_START;
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 25));

		JButton btnAction = new JButton("Sauvegarder");
		configureAction(btnAction);
		btnAction.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnAction = new GridBagConstraints();
		gbc_btnAction.anchor = GridBagConstraints.LINE_START;
		gbc_btnAction.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAction.insets = new Insets(0, 0, 5, 0);
		gbc_btnAction.gridx = 0;
		gbc_btnAction.gridy = 1;
		panel_menu.add(btnAction, gbc_btnAction);

		JButton btnApercu = new JButton("Apercu");
		btnApercu.setAction(actionApercu);
		btnApercu.setForeground(Color.BLACK);
		btnApercu.setHorizontalAlignment(SwingConstants.LEFT);
		btnApercu.setIcon(new ImageIcon(PVoitureAjout.class
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
		btnAccueil.setIcon(new ImageIcon(PVoitureAjout.class
				.getResource("/images/gestion/Cancel-48.png")));
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
		panel_principal.setLayout(new MigLayout("", "[grow]", "[][][][grow]"));

		JPanel panel_formulaire = new JPanel();
		panel_formulaire.setOpaque(false);
		panel_formulaire.setBorder(new CompoundBorder(
				new CompoundBorder(	new EmptyBorder(5, 5, 5, 5), 
									new TitledBorder(
											new LineBorder(new Color(0, 0, 0), 1, true),
											"Voiture", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY)),
				new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_formulaire, "cell 0 0 4 1,grow");
		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 79, 182, 91, 182, 119 };
		gbl_panel_formulaire.rowHeights = new int[] { 26 };
		gbl_panel_formulaire.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				0.0 };
		gbl_panel_formulaire.rowWeights = new double[] { 1.0 };
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
		
		JLabel lblStatus = new JLabel("Location");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.EAST;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 3;
		gbc_lblStatus.gridy = 0;
		panel_formulaire.add(lblStatus, gbc_lblStatus);
		UI.habiller(lblStatus);

		txtStatus = new JTextField();
		lblStatus.setLabelFor(txtStatus);
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.insets = new Insets(0, 0, 5, 5);
		gbc_txtStatus.gridx = 4;
		gbc_txtStatus.gridy = 0;
		panel_formulaire.add(txtStatus, gbc_txtStatus);
		txtStatus.setColumns(10);
		UI.habiller(txtStatus);

//		locationEnCours = new JCheckBox("Location en cours");
//		locationEnCours.setOpaque(false);
//		GridBagConstraints gbc_locationEnCours = new GridBagConstraints();
//		gbc_locationEnCours.anchor = GridBagConstraints.NORTHWEST;
//		gbc_locationEnCours.insets = new Insets(0, 0, 5, 0);
//		gbc_locationEnCours.gridx = 4;
//		gbc_locationEnCours.gridy = 0;
//		panel_formulaire.add(locationEnCours, gbc_locationEnCours);
//		UI.habiller(locationEnCours);

		JPanel panel_etatVoiture = new JPanel();
		panel_etatVoiture.setOpaque(false);
		panel_etatVoiture.setBorder(new CompoundBorder(
				new CompoundBorder(	new EmptyBorder(5, 5, 5, 5), 
									new TitledBorder(
											new LineBorder(new Color(0, 0, 0), 1, true),
											"Type voiture", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY)),
				new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_etatVoiture, "cell 0 1,grow");
		GridBagLayout gbl_panel_etatVoiture = new GridBagLayout();
		gbl_panel_etatVoiture.columnWidths = new int[] { 56, 146, 34, 146, 0 };
		gbl_panel_etatVoiture.rowHeights = new int[] { 26, 26, 0, 0 };
		gbl_panel_etatVoiture.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_etatVoiture.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_etatVoiture.setLayout(gbl_panel_etatVoiture);

		JLabel lblMarque = new JLabel("Marque");
		GridBagConstraints gbc_lblMarque = new GridBagConstraints();
		gbc_lblMarque .anchor = GridBagConstraints.WEST;
		gbc_lblMarque .insets = new Insets(0, 0, 5, 5);
		gbc_lblMarque .gridx = 0;
		gbc_lblMarque .gridy = 0;
		panel_etatVoiture.add(lblMarque, gbc_lblMarque );
		UI.habiller(lblMarque);
		lblMarque.setLabelFor(txtMarque);

		txtMarque = new JTextField();
		GridBagConstraints gbc_txtMarque = new GridBagConstraints();
		gbc_txtMarque.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMarque.weightx = 1.0;
		gbc_txtMarque.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtMarque.insets = new Insets(0, 0, 5, 20);
		gbc_txtMarque.gridx = 1;
		gbc_txtMarque.gridy = 0;
		panel_etatVoiture.add(txtMarque, gbc_txtMarque);
		txtMarque.setColumns(10);
		UI.habiller(txtMarque);

		JLabel lblModele = new JLabel("Modele");
		GridBagConstraints gbc_lblModele = new GridBagConstraints();
		gbc_lblModele.anchor = GridBagConstraints.WEST;
		gbc_lblModele.insets = new Insets(0, 0, 5, 5);
		gbc_lblModele.gridx = 2;
		gbc_lblModele.gridy = 0;
		panel_etatVoiture.add(lblModele, gbc_lblModele);
		UI.habiller(lblModele);

		txtModele = new JTextField();
		lblModele.setLabelFor(txtModele);
		GridBagConstraints gbc_txtModele = new GridBagConstraints();
		gbc_txtModele.weightx = 1.0;
		gbc_txtModele.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtModele.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtModele.insets = new Insets(0, 0, 5, 0);
		gbc_txtModele.gridx = 3;
		gbc_txtModele.gridy = 0;
		panel_etatVoiture.add(txtModele, gbc_txtModele);
		txtModele.setColumns(10);
		UI.habiller(txtModele);

		JLabel lblCarburant = new JLabel("carburant");
		GridBagConstraints gbc_lblCarburant = new GridBagConstraints();
		gbc_lblCarburant.anchor = GridBagConstraints.WEST;
		gbc_lblCarburant.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarburant.gridx = 0;
		gbc_lblCarburant.gridy = 1;
		panel_etatVoiture.add(lblCarburant, gbc_lblCarburant);
		UI.habiller(lblCarburant);

		txtCarburant = new JTextField();
		lblCarburant.setLabelFor(txtCarburant);
		GridBagConstraints gbc_txtCarburant = new GridBagConstraints();
		gbc_txtCarburant.insets = new Insets(0, 0, 5, 0);
		gbc_txtCarburant.gridwidth = 3;
		gbc_txtCarburant.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCarburant.weightx = 1.0;
		gbc_txtCarburant.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtCarburant.gridx = 1;
		gbc_txtCarburant.gridy = 1;
		panel_etatVoiture.add(txtCarburant, gbc_txtCarburant);
		txtCarburant.setColumns(10);
		UI.habiller(txtCarburant);
		
		JPanel panel_remarques = new JPanel();
		panel_remarques.setOpaque(false);
		panel_remarques.setBorder(new CompoundBorder(
				new CompoundBorder(	new EmptyBorder(5, 5, 5, 5), 
									new TitledBorder(
											new LineBorder(new Color(0, 0, 0), 1, true),
											"Remarques", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY)),
				new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_remarques, "cell 0 3,grow");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 79};
		gbl_panel.rowHeights = new int[] { 210 };
		gbl_panel.columnWeights = new double[] { 0.0};
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel_remarques.setLayout(gbl_panel);
		
		textArea = new JTextArea();
		Border cadre = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		textArea.setBorder(cadre);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.weighty = 1.0;
		gbc_textArea.weightx = 1.0;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.anchor = GridBagConstraints.NORTHWEST;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		panel_remarques.add(textArea, gbc_textArea);
		UI.habiller(textArea);
		
		SwingUtilities.invokeLater(
				() -> btnAccueil.requestFocusInWindow()		
		);
	}

	void configureAction(JButton bouton) {
		bouton.setAction(actionPrincipale);
		bouton.setForeground(Color.BLACK);
		bouton.setIcon(new ImageIcon(PVoitureAjout.class
				.getResource("/images/gestion/Save-48.png")));
		UI.deshabillerBouton(bouton, "Save");
	}

	String getIcone() {
		return "/images/gestion/client/User-Add-64.png";
	}

	String getTitre() {
		return "Modifications";
	}

	private final class Formateur extends
			JFormattedTextField.AbstractFormatter {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String valueToString(Object value) throws ParseException {
			String text = "";
			if (value instanceof Instant) {
				Instant instant = (Instant) value;					
				text = GestionDates.dateEnChaineFR(instant);
			}
			return text;
		}

		@Override
		public Object stringToValue(String text) throws ParseException {
			try {
				return GestionDates.chaineFRenDate(text);
			} catch (ParseException e) {
				return null;
			}
		}
	}

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
			configurerAction(this);
		}

		public void actionPerformed(ActionEvent e) {
			String immatriculation = txtImmatriculation.getText();
			String marque = txtMarque.getText();
			String modele = txtModele.getText();
			String carburant = txtCarburant.getText();
			boolean status;
			if (txtStatus.equals("Louée")){
				status=true;
		    }else {
		    	status=false;
		    }


			actionPrincipale(immatriculation, marque, modele, carburant, status);
		}
	}

	protected void configurerAction(AbstractAction action) {
		action.putValue(AbstractAction.NAME, "Sauvegarder");
		action.putValue(AbstractAction.SHORT_DESCRIPTION, "Sauvegarder la nouvelle voiture");
	}

	protected void actionPrincipale(String immatriculation, String marque, String modele, String carburant, boolean status) {
		if (!immatriculation.equals("") && !marque.equals("") && !modele.equals("") && !carburant.equals("") ) {
			controleVoiture.creer(immatriculation, marque, modele, carburant, status);

			// remise à vide des champs pour un nouvel ajout
			txtImmatriculation.setText("");
			txtModele.setText("");
			txtMarque.setText("");
			txtCarburant.setText("");
//			locationEnCours.setSelected(false);
			
			txtImmatriculation.requestFocus();
		} else {
			JOptionPane.showMessageDialog(null, "Un champ n'a pas été saisi "
					 , "Vérifiez votre saisie",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	JLabel getLblTitre() {
		return lblTitre;
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
