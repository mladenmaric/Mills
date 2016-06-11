package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import engine.Boja;
import engine.Engine;
import engine.GameMode;
import engine.Status;

public class IgracListener implements MouseListener
{
	private int index;
	private Boja igrac;
	private Engine engine;
	private Gui gui;
	
	public IgracListener(int index, Boja igrac, Engine engine, Gui gui)
	{
		super();
		this.index = index;
		this.igrac = igrac;
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
				if (igrac == engine.getNaPotezu())
				{
					if (engine.getTrenAktivan() == null)
					{
						if (igrac == Boja.BELA) 
						{
							if (engine.getBeliIgrac(index).getStatus() == Status.POCETAK)
							{
								engine.setTrenAktivan(engine.getBeliIgrac(index));
								gui.ocistiPanelIDodajSliku(gui.getPaneliLevo(index), "/BELA_TAMNO.png");
							}
						}
						else
						{
							if (engine.getCrveniIgrac(index).getStatus() == Status.POCETAK)
							{
								engine.setTrenAktivan(engine.getCrveniIgrac(index));
								gui.ocistiPanelIDodajSliku(gui.getPaneliDesno(index), "/CRVENA_TAMNO.png");
							}
						}
							
					}
					else
					{
						if (engine.getTrenAktivan().getBoja() == igrac && engine.getTrenAktivan().getIndex() == index)
						{
							if (igrac == Boja.BELA)
								gui.ocistiPanelIDodajSliku(gui.getPaneliLevo(index), "/BELA.png");
							else
								gui.ocistiPanelIDodajSliku(gui.getPaneliDesno(index), "/CRVENA.png");
							
							engine.setTrenAktivan(null);
						}
						else
						{
							// obavestiti o pravilima igre!!! -- mora se kliknuti na tablu :)
						}
					}
				}
			}
			else if (engine.getMod() == GameMode.MICA)
			{
				if (engine.getNaPotezu() == igrac)
				{
					if (igrac == Boja.BELA)
					{
						if (engine.getBeliIgrac(index) == null)
						{
							engine.setTablaIgracPrazno(engine.getTrenAktivan());
							engine.getTrenAktivan().setStatus(Status.KRAJ);
							engine.setBeliIgrac(index, engine.getTrenAktivan());
							engine.setTrenAktivan(null);
							engine.postaviMod();
							engine.setNaPotezu();
							
							gui.osveziGui();
						}
					}
					else
					{
						if (engine.getCrveniIgrac(index) == null)
						{
							engine.setTablaIgracPrazno(engine.getTrenAktivan());
							engine.getTrenAktivan().setStatus(Status.KRAJ);
							engine.setCrveniIgrac(index, engine.getTrenAktivan());
							engine.setTrenAktivan(null);
							engine.postaviMod();
							engine.setNaPotezu();
							
							gui.osveziGui();
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
