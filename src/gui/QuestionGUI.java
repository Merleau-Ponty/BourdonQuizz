package gui;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

public class QuestionGUI extends JPanel
{
	private JTextArea libelle;
	private JTextArea corrige;
	private String upload;
	private boolean isUploaded = false;

	public QuestionGUI(boolean ajout)
	{
		initGUI(ajout);
	}

	private void initGUI(boolean ajout)
	{
		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		JLabel title = new JLabel("d'une question");
		title.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(title, gbc);
		if (ajout)
		{
			title.setText("Ajout " + title.getText());
			libelle = new JTextArea("Libellé de la question");
			gbc.insets = new Insets(5, 0, 5, 0);
			libelle.setLineWrap(true);
			libelle.setForeground(Color.GRAY);
			libelle.setFont(getFont());
			gbc.gridy += 1;
			gbc.ipady = 40;
			gbc.ipadx = 40;
			add(new JScrollPane(libelle), gbc);
			gbc.gridy += 1;
			corrige = new JTextArea("Corrigé de la question");
			corrige.setFont(getFont());
			corrige.setForeground(Color.GRAY);
			corrige.setLineWrap(true);
			add(new JScrollPane(corrige), gbc);
			gbc.ipadx = 0;
			gbc.ipady = 0;
			gbc.gridy += 1;
			add(new JButton("Uploader une image"), gbc);
			gbc.insets = new Insets(20, 0, 0, 0);
			gbc.gridy += 1;
			add(new JButton("Enregistrer la question"), gbc);
		}
		else
		{
			title.setText("Modification " + title.getText());
		}
		initListeners();
		addMouseListener(new CustomMouseListener());
	}

	private void initListeners()
	{
		for (Component c : getComponents())
		{
			if (c instanceof JButton)
			{
				c.addMouseListener(new CustomMouseListener());
			}
			else
			{
				c.addFocusListener(new CustomFocusListener());
			}
		}
		libelle.addFocusListener(new CustomFocusListener());
		corrige.addFocusListener(new CustomFocusListener());
	}

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

	// Gestionnaires d'évènement
	private class CustomFocusListener extends FocusAdapter
	{
		public void focusGained(FocusEvent e)
		{
			if (e.getSource() instanceof JTextArea)
			{
				JTextArea a = (JTextArea) e.getSource();
				if (a.getText().equals("Libellé de la question") && a == libelle)
				{
					a.setText("");
					a.setForeground(Color.BLACK);
				}
				else if (a.getText().equals("Corrigé de la question") && a == corrige)
				{
					a.setText("");
					a.setForeground(Color.BLACK);
				}
			}
		}

		public void focusLost(FocusEvent e)
		{
			if (e.getSource() instanceof JTextArea)
			{
				JTextArea a = (JTextArea) e.getSource();
				if (a.getText().equals(""))
				{
					if (a == libelle)
					{
						a.setText("Libellé de la question");
						a.setForeground(Color.GRAY);
					}
					else if (a == corrige)
					{
						a.setText("Corrigé de la question");
						a.setForeground(Color.GRAY);
					}
				}
			}
		}
	}

	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (e.getSource() instanceof JButton)
			{
				switch (((JButton) e.getSource()).getText())
				{
				case "Uploader une image":
					JFileChooser browse = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
					browse.setFileFilter(filter);
					if (browse.showOpenDialog(QuestionGUI.this) == JFileChooser.APPROVE_OPTION)
					{
						File f = browse.getSelectedFile();
						int idQ = DAOFactory.getInstance().getQuestion().selecNbQuestions() + 1;
						sendPostImage(f, idQ);
						String name = f.getName();
						upload = idQ + name.substring(name.lastIndexOf('.'));
						System.out.println("Upload effectué !");
						isUploaded = true;
					}
					break;
				case "Enregistrer la question":
					if(isUploaded)
					{
						DAOFactory.getInstance().getQuestion().enregistrerQuestion(libelle.getText(), corrige.getText(), upload);
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
