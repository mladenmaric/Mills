package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import engine.Engine;
import engine.GameMode;

public class TablaListener implements MouseListener
{
	private int i;
	private int j;
	private Engine engine;
	private Gui gui;
	
	public TablaListener(int i, int j, Engine engine, Gui gui)
	{
		super();
		this.i = i;
		this.j = j;
		this.engine = engine;
		this.gui = gui;
	}

	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			if (engine.getMod() == GameMode.POSTAVLJANJE)
			{
				if (engine.getTrenAktivan() != null)
					try
					{
						engine.postaviIgraca(engine.getTrenAktivan(), i, j);
						engine.setTrenAktivan(null);
						if (engine.getMod() != GameMode.MICA) 
							engine.setNaPotezu();
						
						gui.osveziGui();
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}
			}
			else if (engine.getMod() == GameMode.IGRANJE)
			{ 
			
				if (engine.getTrenAktivan() == null)
				{  
					if (engine.getTablaIJ(i, j).getBoja() == engine.getNaPotezu())
					{
						engine.setTrenAktivan(engine.getTablaIJ(i, j));
						gui.ocistiPanelIDodajSliku(gui.getPaneliTabla(i, j),"/" + engine.getTablaIJ(i, j).getBoja() + "_TAMNO.png");
					}					
				}
				else
				{
					if (engine.getTrenAktivan() == engine.getTablaIJ(i, j))
					{
						engine.setTrenAktivan(null);
						gui.ocistiPanelIDodajSliku(gui.getPaneliTabla(i, j), engine.getTablaIJ(i, j).getImage());
					}
					else
					{ 
						try
						{
							engine.pomeriIgraca(engine.getTrenAktivan(), i, j);
							engine.setTrenAktivan(null);
							if (engine.getMod() != GameMode.MICA) 
								engine.setNaPotezu();								
							
							gui.osveziGui(); 
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
			else if (engine.getMod() == GameMode.MICA) 
			{
				// izaberi protivnickog igraca da pojedes...
				
				if (engine.getTablaIJ(i, j).getBoja() != engine.getNaPotezu())
				{
					if (engine.getTrenAktivan() == null)
					{
						if (!engine.odrediMod() || !engine.isMica(i, j)) // ispraviti!!!
						{
							engine.setTrenAktivan(engine.getTablaIJ(i, j));
							gui.ocistiPanelIDodajSliku(gui.getPaneliTabla(i, j),"/" + engine.getTablaIJ(i, j).getBoja() + "_TAMNO.png");
						}
					}
					else
					{
						if (engine.getTrenAktivan() == engine.getTablaIJ(i, j))
						{
							engine.setTrenAktivan(null);
							gui.ocistiPanelIDodajSliku(gui.getPaneliTabla(i, j), engine.getTablaIJ(i, j).getImage());
						}
					}
				}
			}
		}		
	}
		
	
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
}
