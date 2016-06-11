package gui;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private String backgroundImage;
	
	public MyPanel(String backgroundImage)
	{
		this.backgroundImage = backgroundImage;
	}

	public void paintComponent(Graphics g)
	{
		int x = 0, y = 0;
		
		super.paintComponent(g);
		if (backgroundImage == "/PRAZNINA.png") {x = 7; y = 7;}
		g.drawImage(new ImageIcon(getClass().getResource(backgroundImage)).getImage(), x, y, null);
	}
	
}
