package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import engine.Boja;
import engine.Engine;
import engine.Status;

public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel[][] paneliTabla;
	private MyPanel[] paneliLevo;
	private MyPanel[] paneliDesno;
	private JButton novaIgra;
	private MyPanel pozadina;
	private Engine engine = new Engine();
	
	public Gui()
	{
		setTitle("Mice");
		init();
	}
	
	private void init()
	{
		postaviPozadinu();
		
		postaviGore();
		postaviSredinu();
		postaviLevo();
		postaviDesno();	
		postaviProzor();
		addListeners();
	
		osveziGuiLevo();
		osveziGuiDesno();
		osveziGuiSredina();
	}
	
	private void postaviProzor()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 650);
		setLocationRelativeTo(null);
		pack();
	}
	
	private void postaviPozadinu()
	{
		pozadina = new MyPanel("/back.jpg");
		pozadina.setLayout(new BorderLayout());
		pozadina.setOpaque(false);
		setContentPane(pozadina);
	}
	
	private void postaviGore()
	{
		JPanel gore = new JPanel(new FlowLayout());
		gore.setOpaque(false);
		gore.setPreferredSize(new Dimension(1000, 60));
		pozadina.add(gore, BorderLayout.NORTH);
		
		novaIgra = new JButton(new ImageIcon(getClass().getResource("/newgame.png")));
		novaIgra.setOpaque(false);
		novaIgra.setContentAreaFilled(false);
		novaIgra.setBorderPainted(false);
		novaIgra.setPreferredSize(new Dimension(263, 50));
		
		gore.add(novaIgra);		
	}
	
	private void postaviSredinu()
	{
		
		MyPanel centar = new MyPanel("/background.png");
		centar.setOpaque(false);
		centar.setBorder(new EmptyBorder(5, 5, 5, 5));
		centar.setLayout(null);
		centar.setPreferredSize(new Dimension(600, 600));
		pozadina.add(centar, BorderLayout.CENTER);
		
		paneliTabla = new JPanel[7][7];
		
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++)
				if ((i == j || i + j == 6 || i == 3 || j == 3) && (i != 3 || j != 3))
				{
					paneliTabla[i][j] = new JPanel();
					paneliTabla[i][j].setOpaque(false);
					paneliTabla[i][j].setBounds(90 * j, 90 * i, 60, 60);
					
					FlowLayout flow = (FlowLayout) paneliTabla[i][j].getLayout();
					flow.setHgap(10);
					flow.setVgap(10);
					
					centar.add(paneliTabla[i][j]);
				}
		
	}
	
	private void postaviLevo()
	{
		
		JPanel levo = new JPanel(null);
		levo.setOpaque(false);
		levo.setPreferredSize(new Dimension(200, 600));
		pozadina.add(levo, BorderLayout.WEST);
		
		paneliLevo = new MyPanel[9];
		String background = "/PRAZNINA.png";
		int visina = 11;
		MyPanel panel;
		
		for (int i = 0; i < 9; i++)
		{
			panel = new MyPanel(background);
			
			paneliLevo[i] = panel;
			paneliLevo[i].setOpaque(false);
			paneliLevo[i].setBounds(72, visina, 55, 55);
			
			levo.add(paneliLevo[i]);
			visina += (i % 2) + 65;
		}
		
	}
	
	private void postaviDesno()
	{
		JPanel desno = new JPanel(null);
		desno.setOpaque(false);
		desno.setPreferredSize(new Dimension(200, 600));
		pozadina.add(desno, BorderLayout.EAST);
		
		paneliDesno = new MyPanel[9];
		String background = "/PRAZNINA.png";
		int visina = 11;
		
		for (int i = 0; i < 9; i++)
		{
			paneliDesno[i] = new MyPanel(background);
			paneliDesno[i].setOpaque(false);
			paneliDesno[i].setBounds(72, visina, 55, 55);
			
			desno.add(paneliDesno[i]);
			visina += (i % 2) + 65;
		}
		
	}
	
	private void addListeners()
	{
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++)
				if (paneliTabla[i][j] != null)
					paneliTabla[i][j].addMouseListener(new TablaListener(i, j, engine, this));
		
		for (int i = 0; i < 9; i++)
			paneliLevo[i].addMouseListener(new IgracListener(i, Boja.BELA, engine, this));
		
		for (int i = 0; i < 9; i++)
			paneliDesno[i].addMouseListener(new IgracListener(i, Boja.CRVENA, engine, this));
		
		novaIgra.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				engine.init();
				osveziGui();
			}
		});
	}

	
	public void osveziGuiLevo()
	{
		for (int i = 0; i < 9; i++)
			if (engine.getBeliIgrac(i) != null)
			{
				if (engine.getBeliIgrac(i).getStatus() == Status.POCETAK || engine.getBeliIgrac(i).getStatus() == Status.KRAJ)
					ocistiPanelIDodajSliku(paneliLevo[i], engine.getBeliIgrac(i).getImage());
			}
			else ocistiPanelIDodajSliku(paneliLevo[i], null);
	}
	
	public void osveziGuiDesno()
	{
		for (int i = 0; i < 9; i++)
			if (engine.getCrveniIgrac(i) != null)
			{
				if (engine.getCrveniIgrac(i).getStatus() == Status.POCETAK || engine.getCrveniIgrac(i).getStatus() == Status.KRAJ)
					ocistiPanelIDodajSliku(paneliDesno[i], engine.getCrveniIgrac(i).getImage());
			}
			else ocistiPanelIDodajSliku(paneliDesno[i], null);
	}
	
	public void osveziGuiSredina()
	{
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++)
				if (engine.getTablaIJ(i, j) != null)
				{
					if (engine.getTablaIJ(i, j).getIndex() != -1)
						ocistiPanelIDodajSliku(paneliTabla[i][j], engine.getTablaIJ(i, j).getImage());
					else
						ocistiPanelIDodajSliku(paneliTabla[i][j], null);
				}
	}
	
	public void osveziGui()
	{
		osveziGuiLevo();
		osveziGuiDesno();
		osveziGuiSredina();
	}

	public MyPanel getPaneliLevo(int index)
	{
		return paneliLevo[index];
	}

	public MyPanel getPaneliDesno(int index)
	{
		return paneliDesno[index];
	}
	
	public JPanel getPaneliTabla(int i, int j)
	{
		return paneliTabla[i][j];
	}
	
	public void ocistiPanelIDodajSliku(JPanel panel, String image)
	{
		panel.removeAll();
		if (image != null) panel.add(new SlikaLabel(image));
		panel.revalidate();
		panel.repaint();
	}
	
}
