package gui.quizz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dao.DAOFactory;
import gui.MenuChoix;
import main.MainFrame;
import metier.Proposition;
import metier.Question;
import metier.Tentative;

public class JouerQuizz extends JPanel
{
	private final static String URL = DAOFactory.weburl + "/img/";
	private final static String IMG_PROP_CORRECTE = URL + "prop_correcte.png";
	private final static String IMG_PROP_FAUSSE = URL + "prop_incorrecte.png";
	
	private ArrayList<Question> listeQuestions;
	private ArrayList<Proposition> listePropositions;
	private ArrayList<Boolean> validiteRep = new ArrayList<Boolean>();
	private JPanel layoutProp;
	private int indiceQuestion = 0;
	private int idQuizz;	
	private int idTentative;
	
	public JouerQuizz(int idQuizz)
	{
		this.idQuizz = idQuizz;
		listeQuestions  = DAOFactory.getInstance().getQuizz().selecQuestionsQuizz(this.idQuizz);
		initGUI();
	}
	
	private void initGUI()
	{
		removeAll();
		listePropositions = DAOFactory.getInstance().getProposition().selecPropQuestion(listeQuestions.get(indiceQuestion).getIdQuestion());
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel imgLabel = null;
		try
		{
			ImageIcon imgIcon = new ImageIcon(ImageIO.read(new URL(URL + listeQuestions.get(indiceQuestion).getPhoto())));
			int width = imgIcon.getIconWidth();
			int height = imgIcon.getIconHeight();
			while(width > 800 || height > 600)
			{
				width *= 0.9;
				height *= 0.9;
			}
			Image scaledImg = imgIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			imgLabel = new JLabel(new ImageIcon(scaledImg));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		gbc.insets.bottom = 10;
		add(imgLabel, gbc);
		
		gbc.gridy += 1;
		JLabel enonce = new JLabel(listeQuestions.get(indiceQuestion).getEnonce());
		enonce.setFont(new Font("Roboto", Font.PLAIN, 30));
		add(enonce, gbc);
		
		layoutProp = genPropPanel();
		gbc.gridy += 1;
		gbc.insets.bottom = 30;
		add(layoutProp, gbc);
		
		gbc.gridy += 1;
		gbc.insets.bottom = 5;
		add(new JButton("Réinitialiser les réponses"), gbc);
		
		gbc.gridy += 1;
		gbc.insets.bottom = 0;
		add(new JButton("Valider les réponses"), gbc);
		
		initMouseListeners(this);
		initMouseListeners(layoutProp);
		setBorder(BorderFactory.createEmptyBorder(5, 100, 5, 100));
	}
	
	private JPanel genPropPanel()
	{
		JPanel layoutProp = new JPanel();
		layoutProp.setLayout(new GridBagLayout());
		GridBagConstraints gbcProps = new GridBagConstraints();
		gbcProps.insets.bottom = 5;
		gbcProps.gridx = 0;
		gbcProps.gridy = 0;
		layoutProp.add(new JButton("(A) " + listePropositions.get(0).getEnonce()), gbcProps);
		gbcProps.gridx += 1;
		layoutProp.add(new JButton("(B) " + listePropositions.get(1).getEnonce()), gbcProps);
		if(listePropositions.size() > 2)
		{
			gbcProps.insets.bottom = 0;
			gbcProps.gridx = 0;
			gbcProps.gridy += 1;
			layoutProp.add(new JButton("(C) " + listePropositions.get(2).getEnonce()), gbcProps);
			if(listePropositions.size() > 3)
			{
				gbcProps.gridx += 1;
				layoutProp.add(new JButton("(D) " + listePropositions.get(3).getEnonce()), gbcProps);
			}
		}
		equalizeButtonsSize(layoutProp);
		return layoutProp;
	}
	
	private void equalizeButtonsSize(JPanel layout)
	{
		Dimension dim = null;
		Double dimWidth = 0d;
		for(Component c : layout.getComponents())
		{
			Double currentWidth = c.getPreferredSize().getWidth();
			if(currentWidth > dimWidth)
			{
				dimWidth = currentWidth;
				dim = c.getPreferredSize();
			}
		}
		for(Component c : layout.getComponents())
		{
			c.setPreferredSize(dim);
		}
	}
	
	private void initMouseListeners(Container cont)
	{
		for(Component c : cont.getComponents())
		{
			if(c instanceof JButton)
				c.addMouseListener(new CustomMouseListener());;
		}
	}
	
	private void changeComponent(Container cont, Component c,GridBagConstraints gbc, int order)
	{
		cont.remove(cont.getComponent(order));
		cont.add(c, gbc);
	}
	
	private void applyChanges()
	{
		revalidate();
		repaint();
	}
	
	private void correctAnswers()
	{	
		DAOFactory dao = DAOFactory.getInstance();
		if(indiceQuestion == 0)
			idTentative = dao.getTentative().ajouter(new Tentative(new Date(), 0), MenuChoix.joueur.getId_joueur(), idQuizz);
		boolean res = true;
		int i = 0;
		GridBagConstraints gbcProp = new GridBagConstraints();
		gbcProp.gridx = 1;
		gbcProp.gridy = 0;
		gbcProp.anchor = GridBagConstraints.WEST;
		gbcProp.insets = new Insets(0, 0, 5, 0);
		Component[] comps = layoutProp.getComponents();
		JButton toReplace;
		for(Component c : comps)
		{
			boolean checked = !((JButton)c).isEnabled();
			dao.getReponse().ajouter(listeQuestions.get(indiceQuestion).getIdQuestion(), idTentative, checked);
			switch(i)
			{
				case 0:
					validiteRep.add(checked);
					validiteRep.add(!((JButton)comps[1]).isEnabled());
					toReplace = ((JButton)comps[1]);
					String icone = (validiteRep.get(0) == listePropositions.get(0).getValide() ? IMG_PROP_CORRECTE : IMG_PROP_FAUSSE);
					if(icone == IMG_PROP_FAUSSE)
						res = false;
					try
					{
						changeComponent(layoutProp, new JLabel(new ImageIcon(ImageIO.read(new URL(icone)))), gbcProp, 1);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					gbcProp.gridx += 1;
					layoutProp.add(toReplace, gbcProp);
					gbcProp.gridx += 1;
					icone = (validiteRep.get(1) == listePropositions.get(1).getValide() ? IMG_PROP_CORRECTE : IMG_PROP_FAUSSE);
					if(icone == IMG_PROP_FAUSSE)
						res = false;
					try
					{
						layoutProp.add(new JLabel(new ImageIcon(ImageIO.read(new URL(icone)))), gbcProp);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				case 2:
					validiteRep.add(checked);
					if(comps.length > 2)
					{
						gbcProp.insets.bottom = 0;
						gbcProp.gridy = 1;
						gbcProp.gridx = 1;
						
						icone = (validiteRep.get(2) == listePropositions.get(2).getValide() ? IMG_PROP_CORRECTE : IMG_PROP_FAUSSE);
						if(icone == IMG_PROP_FAUSSE)
							res = false;
						gbcProp.gridx = 1;
						gbcProp.insets.right = 20;
						try
						{
							layoutProp.add(new JLabel(new ImageIcon(ImageIO.read(new URL(icone)))), gbcProp);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						if(comps.length > 3)
						{
							validiteRep.add(!((JButton)comps[3]).isEnabled());
							toReplace = (JButton)comps[3];
							icone = (validiteRep.get(3) == listePropositions.get(3).getValide() ? IMG_PROP_CORRECTE : IMG_PROP_FAUSSE);
							if(icone == IMG_PROP_FAUSSE)
								res = false;
							gbcProp.insets.right = 0;
							gbcProp.gridx++;
							layoutProp.add(toReplace, gbcProp);
							gbcProp.gridx++;
							try
							{
								layoutProp.add(new JLabel(new ImageIcon(ImageIO.read(new URL(icone)))), gbcProp);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
					break;
			}
			i++;
		}
		if(res)
		{
			dao.getTentative().updateScore(idTentative);
		}
		JButton comp = (JButton)getComponent(4);
		if(indiceQuestion + 1 == listeQuestions.size())
			comp.setText("Revenir au menu");
		else
			comp.setText("Question suivante");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets.bottom = 5;
		JLabel corrige = new JLabel(listeQuestions.get(indiceQuestion).getCorrige());
		corrige.setFont(new Font("Roboto", Font.PLAIN, 20));
		corrige.setForeground(Color.BLUE);
		add(corrige, gbc);
		changeComponent(this, corrige, gbc, 3);
		applyChanges();
		indiceQuestion++;
	}
	
	// Gestionnaire d'évènements
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if(e.getSource() instanceof JButton)
			{
				switch(((JButton)e.getSource()).getText())
				{
					case "Réinitialiser les réponses":
						for(Component c : layoutProp.getComponents())
						{
							((JButton)c).setEnabled(true);
						}
						break;
					case "Valider les réponses":
						correctAnswers();
						break;
					case "Question suivante":
						initGUI();
						validiteRep = new ArrayList<Boolean>();
						((MainFrame)SwingUtilities.getWindowAncestor(JouerQuizz.this)).applyChanges();
						break;
					case "Revenir au menu":
						((MainFrame)SwingUtilities.getWindowAncestor(JouerQuizz.this)).changePanel(new MenuChoix());
						break;
					default:
						String[] indicesProp = {"A", "B", "C", "D"};
						JButton src = (JButton)e.getSource();
						if(Arrays.asList(indicesProp).contains(src.getText().substring(1, 2)))
						{
							src.setEnabled(false);
						}
						break;
				}
			}
		}
	}
}
