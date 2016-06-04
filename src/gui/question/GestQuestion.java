package gui.question;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import dao.DAOFactory;
import dao.PropositionDAO;
import dao.QuestionDAO;
import gui.MenuChoix;
import main.MainFrame;
import metier.Proposition;
import metier.Question;

/**
 * Classe d�finissant un JPanel g�rant la modification et la cr�ation d'une question
 * @author BourdonQuizz
 */
public class GestQuestion extends JPanel
{
	private JLabel titre;
	private JTextArea libelle;
	private JTextArea corrige;
	private JButton valider;
	private JButton ajouterProp;
	private JLabel erreurUpload;
	private JLabel erreurValidation;
	private ArrayList<JTextField> props = new ArrayList<JTextField>();
	private ArrayList<JCheckBox> propsCheck = new ArrayList<JCheckBox>();

	private ArrayList<String> enoncesPropAvantModif;
	private int nbQuestionsModifBase;
	private Dimension areaPreferredSize;
	private Dimension textPreferredSize;
	private int idQModification;
	private String upload;
	private boolean isErrorDisplayed = false;
	private boolean isUploaded = false;
	
	/**
	 * Constructeur initialisant l'interface graphique
	 */
	public GestQuestion()
	{
		initGUI();
	}

	/**
	 * M�thode initialisant l'interface graphique de modification d'une question
	 * @param idQ identifiant de la question concern�e
	 */
	public GestQuestion(int idQ)
	{
		initGUI();
		titre.setText("Modifier une question");
		DAOFactory dao = DAOFactory.getInstance();
		valider.setText("Mettre � jour la question et ses r�ponses");
		Question q = dao.getQuestion().selecParId(idQ);
		libelle.setText(q.getEnonce());
		corrige.setText(q.getCorrige());
		upload = q.getPhoto();
		ArrayList<Proposition> pArrayList = dao.getProposition().selecPropQuestion(idQ);
		nbQuestionsModifBase = pArrayList.size();
		enoncesPropAvantModif = new ArrayList<String>();
		for(int i = 0; i < pArrayList.size(); i++)
		{
			if(i >= 1)
			{
				ajouterProposition(pArrayList.get(i).getEnonce());
			}
			else
			{
				props.get(i).setText(pArrayList.get(i).getEnonce());
			}
			propsCheck.get(i).setSelected(pArrayList.get(i).getValide());
			enoncesPropAvantModif.add(pArrayList.get(i).getEnonce());
		}
		for(Component c : getComponents())
		{
			if(c instanceof JTextField)
				c.setForeground(Color.BLACK);
		}
		libelle.setForeground(Color.BLACK);
		corrige.setForeground(Color.BLACK);
		for(JTextField j : props)
		{
			j.setPreferredSize(textPreferredSize);
		}
		idQModification = idQ;
		isUploaded = true;
	}

	/**
	 * M�thode initialisant l'interface graphique pour la cr�ation d'une question
	 */
	private void initGUI()
	{
		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		titre = new JLabel("Ajout d'une question");
		titre.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(titre, gbc);
		libelle = new JTextArea("Libell� de la question");
		gbc.insets = new Insets(5, 0, 5, 0);
		libelle.setLineWrap(true);
		libelle.setForeground(Color.GRAY);
		libelle.setFont(getFont());
		gbc.gridy += 1;
		gbc.ipady = 40;
		add(new JScrollPane(libelle), gbc);
		areaPreferredSize = new Dimension((int) libelle.getPreferredSize().getWidth() + 100,
				(int) libelle.getPreferredSize().getHeight());
		gbc.gridy += 1;
		corrige = new JTextArea("Corrig� de la question");
		corrige.setFont(getFont());
		corrige.setForeground(Color.GRAY);
		corrige.setLineWrap(true);
		add(new JScrollPane(corrige), gbc);
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.gridy += 1;
		add(new JButton("Uploader une image"), gbc);
		gbc.insets = new Insets(20, 0, 5, 0);
		gbc.gridy += 1;
		gbc.ipadx = 110;
		JTextField propA = new JTextField("Proposition A");
		props.add(propA);
		add(propA, gbc);
		textPreferredSize = propA.getPreferredSize();
		JCheckBox checkBox = new JCheckBox();
		propsCheck.add(checkBox);
		gbc.gridx += 1;
		gbc.insets = new Insets(20, -140, 5, 0);
		gbc.ipadx = 0;
		add(checkBox, gbc);
		gbc.gridx = 0;
		gbc.gridy += 1;
		gbc.insets = new Insets(5, 0, 5, 0);
		ajouterProp = new JButton("Ajouter une proposition");
		add(ajouterProp, gbc);
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridy += 1;
		gbc.ipadx = 0;
		valider = new JButton("Enregistrer la question et ses r�ponses");
		add(valider, gbc);

		setAreaCustomPreferedSize();
		initListeners();
		addMouseListener(new CustomMouseListener());
	}

	/**
	 * M�thode permettant d'ajouter une nouvelle proposition vide
	 */
	public void ajouterProposition()
	{
		String[] lettresProp = { "B", "C", "D" };
		if(props.size() <= 3)
		{
			int coordX = props.size();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 5 + coordX;
			add(ajouterProp, gbc);
			gbc.insets = new Insets(20, 0, 0, 0);
			gbc.gridy = 6 + coordX;
			add(valider, gbc);
			JTextField newProp = new JTextField("Proposition " + lettresProp[coordX - 1]);
			props.add(newProp);
			gbc.insets = new Insets(5, 0, 5, 0);
			gbc.gridy = 4 + coordX;
			gbc.ipadx = 110;
			add(newProp, gbc);
			gbc.gridx += 1;
			gbc.ipadx = 0;
			gbc.insets = new Insets(5, -140, 5, 0);
			JCheckBox checkBox = new JCheckBox();
			propsCheck.add(checkBox);
			add(checkBox, gbc);
			newProp.setForeground(Color.GRAY);
			newProp.addFocusListener(new CustomFocusListener());
			setAreaCustomPreferedSize();

			((MainFrame) SwingUtilities.getWindowAncestor(GestQuestion.this)).applyChanges();
		}
	}

	/**
	 * M�thode permettant d'ajouter une proposition avec du texte
	 * @param txt �nonc� de la proposition
	 */
	private void ajouterProposition(String txt)
	{
		if(props.size() <= 3)
		{
			int coordX = props.size();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 5 + coordX;
			add(ajouterProp, gbc);
			gbc.insets = new Insets(20, 0, 0, 0);
			gbc.gridy = 6 + coordX;
			add(valider, gbc);
			JTextField newProp = new JTextField(txt);
			props.add(newProp);
			gbc.insets = new Insets(5, 0, 5, 0);
			gbc.gridy = 4 + coordX;
			gbc.ipadx = 110;
			add(newProp, gbc);
			gbc.gridx += 1;
			gbc.ipadx = 0;
			gbc.insets = new Insets(5, -140, 5, 0);
			JCheckBox checkBox = new JCheckBox();
			propsCheck.add(checkBox);
			add(checkBox, gbc);
			newProp.setForeground(Color.GRAY);
			newProp.addFocusListener(new CustomFocusListener());
			setAreaCustomPreferedSize();
		}
	}

	/**
	 * M�thode permettant de fixer une taille pour les JTextArea
	 */
	private void setAreaCustomPreferedSize()
	{
		libelle.setPreferredSize(areaPreferredSize);
		corrige.setPreferredSize(areaPreferredSize);
	}

	/**
	 * M�thode permettant d'initialiser les �couteurs de tous les composants
	 */
	private void initListeners()
	{
		for(Component c : getComponents())
		{
			if(c instanceof JButton)
			{
				c.addMouseListener(new CustomMouseListener());
			}
			else if(c instanceof JTextField)
			{
				c.setForeground(Color.GRAY);
				c.addFocusListener(new CustomFocusListener());
			}
			else
			{
				c.addFocusListener(new CustomFocusListener());
			}
		}
		libelle.addFocusListener(new CustomFocusListener());
		corrige.addFocusListener(new CustomFocusListener());
	}

	/**
	 * M�thode permettant de v�rifier que les propositions ont bien �t� rempli
	 * @return true si toutes les propositions ont �t� remplies, sinon false
	 */
	private boolean validPropositions()
	{
		boolean res = true;
		String[] s = { "A", "B", "C", "D" };
		int i = 0;
		for(JTextField j : props)
		{
			if(j.getText().equals("Proposition " + s[i]))
				res = false;
			i++;
		}
		return res;
	}

	/**
	 * M�thode affichant une erreur si une image n'est pas choisie et/ou si au moins 2 propositions ne sont pas renseign�es
	 */
	private void displayError()
	{
		if(!isErrorDisplayed)
		{
			erreurValidation = new JLabel("Choisissez une image ou renseignez toutes les propositions (2 min.)");
			erreurValidation.setForeground(Color.RED);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 6 + props.size();
			add(erreurValidation, gbc);
			isErrorDisplayed = true;
			((MainFrame) SwingUtilities.getWindowAncestor(this)).applyChanges();

		}
	}

	/**
	 * M�thode permettant d'uploader une image sur le serveur via une requ�te HTTP
	 * @param f objet File d�signant le fichier � uploader
	 * @param idQ identifiant de la question concern�e
	 */
	private void sendPostImage(File f, int idQ)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost uploadFile = new HttpPost(DAOFactory.weburl + "/uploadProcessing.php?q_id=" + idQ);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody("upfile", f, ContentType.APPLICATION_OCTET_STREAM, f.getName());
		HttpEntity multipart = builder.build();
		uploadFile.setEntity(multipart);
		CloseableHttpResponse response = null;
		try
		{
			response = httpClient.execute(uploadFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		response.getEntity();
	}

	// Gestionnaires d'�v�nements
	/**
	 * Classe interne d�finissant un �couteur d'�v�nements de focus
	 * @author BourdonQuizz
	 */
	private final class CustomFocusListener extends FocusAdapter
	{
		/**
		 * M�thode d�clench�e lorsqu'un composant prend le focus
		 */
		public void focusGained(FocusEvent e)
		{
			if(e.getSource() instanceof JTextArea)
			{
				JTextArea a = (JTextArea) e.getSource();
				if(a.getText().equals("Libell� de la question") && a == libelle)
				{
					a.setText("");
					a.setForeground(Color.BLACK);
				}
				else if(a.getText().equals("Corrig� de la question") && a == corrige)
				{
					a.setText("");
					a.setForeground(Color.BLACK);
				}
			}
			else if(e.getSource() instanceof JTextField)
			{
				JTextField f = (JTextField) e.getSource();
				String[] s = { "A", "B", "C", "D" };
				int i = 0;
				for(JTextField field : props)
				{
					if(field == f)
					{
						if(field.getText().equals("Proposition " + s[i]))
						{
							field.setText("");
							field.setForeground(Color.BLACK);
						}
					}
					i++;
				}
			}
		}

		/**
		 * M�thode d�clench�e lorsqu'un composant perd le focus
		 */
		public void focusLost(FocusEvent e)
		{
			if(e.getSource() instanceof JTextArea)
			{
				JTextArea a = (JTextArea) e.getSource();
				if(a.getText().equals(""))
				{
					if(a == libelle)
					{
						a.setText("Libell� de la question");
						a.setForeground(Color.GRAY);
					}
					else if(a == corrige)
					{
						a.setText("Corrig� de la question");
						a.setForeground(Color.GRAY);
					}
				}
			}
			else if(e.getSource() instanceof JTextField)
			{
				JTextField f = (JTextField) e.getSource();
				String[] s = { "A", "B", "C", "D" };
				int i = 0;
				for(JTextField field : props)
				{
					if(field == f)
					{
						if(field.getText().equals(""))
						{
							field.setText("Proposition " + s[i]);
							field.setForeground(Color.GRAY);
						}
					}
					i++;
				}
			}
		}
	}

	/**
	 * Classe interne d�finissant un �couteur d'�v�nements de souris
	 * @author BourdonQuizz
	 */
	private final class CustomMouseListener extends MouseAdapter
	{
		/**
		 * M�thode d�clench�e lors d'un clic sur un composant
		 */
		public void mouseClicked(MouseEvent e)
		{
			if(e.getSource() instanceof JButton)
			{
				switch(((JButton) e.getSource()).getText())
				{
				case "Uploader une image":
					JFileChooser browse = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Image (.jpg, .jpeg, .png)", "jpg", "jpeg", "png");
					browse.setFileFilter(filter);
					if(browse.showOpenDialog(GestQuestion.this) == JFileChooser.APPROVE_OPTION)
					{
						File f = browse.getSelectedFile();
						Integer idQ = null;
						QuestionDAO daoF = DAOFactory.getInstance().getQuestion();
						if(valider.getText().equals("Mettre � jour la question et ses r�ponses"))
						{
							String s = daoF.selecPhotoWithQID(idQModification);
							idQ = Integer.parseInt(s.substring(0, s.lastIndexOf('.')));
						}
						else 
						{
							idQ = daoF.selecMaxNbQuestions() + 1;
						}
						sendPostImage(f, idQ);
						String name = f.getName();
						upload = idQ + name.substring(name.lastIndexOf('.'));
						GridBagConstraints gbc = new GridBagConstraints();
						gbc.gridx = 1;
						gbc.gridy = 3;
						gbc.insets = new Insets(0, -140, 0, 0);
						if(erreurUpload != null)
							GestQuestion.this.remove(erreurUpload);
						erreurUpload = new JLabel("Upload effectu�");
						erreurUpload.setForeground(Color.DARK_GRAY);
						isUploaded = true;
						// V�rification de la taille du fichier
						if(f.length() > 870358)
						{
							erreurUpload.setForeground(Color.RED);
							erreurUpload.setText("Fichier trop gros");
							isUploaded = false;
						}
						GestQuestion.this.add(erreurUpload, gbc);
						((MainFrame) SwingUtilities.getWindowAncestor(GestQuestion.this)).applyChanges();
						setAreaCustomPreferedSize();
						
					}
					break;
				case "Ajouter une proposition":
					if(isErrorDisplayed)
						remove(erreurValidation);
					ajouterProposition();
					break;
				case "Enregistrer la question et ses r�ponses":
					if(isUploaded && validPropositions() && props.size() >= 2)
					{
						DAOFactory daoF = DAOFactory.getInstance();
						upload = (upload.substring(upload.lastIndexOf('.') + 1, upload.length()).equals("jpg") ? 
								upload.substring(0, upload.lastIndexOf('.') + 1) + "jpeg" : upload);
						int idQ = daoF.getQuestion()
								.enregistrerQuestion(new Question(libelle.getText(), corrige.getText(), upload));
						PropositionDAO propDAO = daoF.getProposition();
						int i = 0;
						for(JTextField j : props)
						{
							propDAO.enregistrerProp(new Proposition(j.getText(), propsCheck.get(i).isSelected()), idQ);
							i++;
						}
						((MainFrame) SwingUtilities.getWindowAncestor(GestQuestion.this)).changePanel(new MenuChoix());
					}
					else
					{
						displayError();
					}
					break;
				case "Mettre � jour la question et ses r�ponses":
					if(isUploaded && validPropositions())
					{
						DAOFactory daoF = DAOFactory.getInstance();
						upload = (upload.substring(upload.lastIndexOf('.') + 1, upload.length()).equals("jpg") ? 
								upload.substring(0, upload.lastIndexOf('.') + 1) + "jpeg" : upload);
						daoF.getQuestion().update(new Question(libelle.getText(), corrige.getText(), upload), idQModification);
						int i = 0;
						PropositionDAO propDAO = daoF.getProposition();
						for(JTextField j : props)
						{
							if(i + 1 <= nbQuestionsModifBase)
							{
								int idProp = propDAO.selecIdPropParEnonceEtQuestion(enoncesPropAvantModif.get(i), idQModification);
								propDAO.updateProposition(j.getText(), idQModification, propsCheck.get(i).isSelected(), idProp);
							}
							else
							{
								propDAO.enregistrerProp(new Proposition(j.getText(), propsCheck.get(i).isSelected()), idQModification);
							}
							i++;
						}
						((MainFrame) SwingUtilities.getWindowAncestor(GestQuestion.this)).changePanel(new MenuChoix());
					}
					else 
					{
						displayError();
					}
					break;
				}
			}
			else
			{
				grabFocus();
			}
		}
	}
}
