package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main
{
	public static void main(String[] args) throws UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, ClassNotFoundException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new MainFrame();
			}
		});
	}
}
