package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SlikaLabel extends JLabel
{
	private static final long serialVersionUID = 1L;
	
	public SlikaLabel(String image)
	{
		this.setIcon(new ImageIcon(getClass().getResource(image)));
	}
}
