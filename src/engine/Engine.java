package engine;

public class Engine
{
	private Igrac[][] tabla;
	private Igrac[] beliIgrac;
	private Igrac[] crveniIgrac;
	private Igrac trenAktivan;
	private Boja naPotezu;
	private GameMode mod;
	
	public Engine()
	{
		init();
	}

	public void init()
	{
		naPotezu = Boja.BELA;
		mod = GameMode.POSTAVLJANJE;
		pocetnePozicije();
	}
	
	private void pocetnePozicije()
	{
		beliIgrac = new Igrac[9];
		for (int i = 0; i < beliIgrac.length; i++)
			beliIgrac[i] = new Igrac(i, Boja.BELA, "/BELA.png", Status.POCETAK);
		
		crveniIgrac = new Igrac[9];
		for (int i = 0; i < crveniIgrac.length; i++)
			crveniIgrac[i] = new Igrac(i, Boja.CRVENA, "/CRVENA.png", Status.POCETAK);

		tabla = new Igrac[7][7];
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++)
				if ((i == j || i + j == 6 || i == 3 || j == 3) && (i != 3 || j != 3))
					tabla[i][j] = new Igrac(-1, Boja.NEPOZNATA, "", Status.IGRA);
	}
	
	public Igrac getBeliIgrac(int index)
	{
		return beliIgrac[index];
	}
	
	public void setBeliIgrac(int index, Igrac igrac)
	{
		beliIgrac[index] = igrac;
	}

	public Igrac getCrveniIgrac(int index)
	{
		return crveniIgrac[index];
	}
	
	public void setCrveniIgrac(int index, Igrac igrac)
	{
		crveniIgrac[index] = igrac;
	}
	
	public Igrac getTablaIJ(int i, int j)
	{
		return tabla[i][j];
	}
	
	public void setTablaIgracPrazno(Igrac igrac)
	{
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++)
				if (tabla[i][j] != null && tabla[i][j].getIndex() == igrac.getIndex() && tabla[i][j].getBoja() == igrac.getBoja())
				{
					tabla[i][j] = new Igrac(-1, Boja.NEPOZNATA, "", Status.IGRA);
					return;
				}
	}

	public Igrac getTrenAktivan()
	{
		return trenAktivan;
	}

	public void setTrenAktivan(Igrac trenAktivan)
	{
		this.trenAktivan = trenAktivan;
	}

	public Boja getNaPotezu()
	{
		return naPotezu;
	}

	public void setNaPotezu()
	{
		if (naPotezu == Boja.BELA)
			naPotezu = Boja.CRVENA;
		else
			naPotezu = Boja.BELA;
	}

	public GameMode getMod()
	{
		return mod;
	}
	
	public void setMod(GameMode mod)
	{
		this.mod = mod;
	}
	
	private void ukloniIzKucice(Igrac igrac)
	{
		if (igrac.getBoja() == Boja.BELA)
			beliIgrac[igrac.getIndex()] = null;
		else if (igrac.getBoja() == Boja.CRVENA)
			crveniIgrac[igrac.getIndex()] = null;
	}
	
	public void postaviIgraca(Igrac igrac, int x, int y) throws Exception
	{
		if (tabla[x][y] != null && tabla[x][y].getIndex() != -1)
			throw new Exception("Vec je postavljen igrac na ovom polju!");
		
		igrac.setStatus(Status.IGRA);
		tabla[x][y] = igrac;
		
		postaviMod();
		if (isMica(x, y))
			mod = GameMode.MICA;
		
		ukloniIzKucice(igrac);
	}
	
	
	public void pomeriIgraca(Igrac igrac, int x, int y) throws Exception
	{
		int i = -1, j = -1;
		boolean nasao = false;
		
		for (int p = 0; p < 7; p++)
			for (int q = 0; q < 7; q++)
			{
				if (tabla[p][q] != null && tabla[p][q].getBoja() == igrac.getBoja() && tabla[p][q].getIndex() == igrac.getIndex())
				{
					i = p;
					j = q;
					nasao = true;
					break;
				}
				
				if (nasao) break;
			}
		
		if (i == x || j == y)
		{
			if (i == x)
			{
				if (j > y)
				{
					for (int p = j - 1; p >= 0; p--)
						if (tabla[i][p] != null)
						{
							if (p == y && tabla[i][p].getIndex() == -1) 
							{
								tabla[i][j] = tabla[i][p];
								tabla[i][p] = igrac;
								break;
							}
							else throw new Exception("Neko je vec na tom polju!");
						}
				}
				else
				{
					for (int p = j + 1; p < 7; p++)
						if (tabla[i][p] != null)
						{
							if (p == y && tabla[i][p].getIndex() == -1) 
							{
								tabla[i][j] = tabla[i][p];
								tabla[i][p] = igrac;
								break;
							}
							else throw new Exception("Neko je vec na tom polju!");
						}
				}
			}
			else
			{
				if (i > x)
				{
					for (int p = i - 1; p >= 0; p--)
						if (tabla[p][j] != null)
						{
							if (p == x && tabla[p][j].getIndex() == -1) 
							{
								tabla[i][j] = tabla[p][j];
								tabla[p][j] = igrac;
								break;
							}
							else throw new Exception("Neko je vec na tom polju!");
						}
				}
				else
				{
					for (int p = i + 1; p < 7; p++)
						if (tabla[p][j] != null)
						{
							if (p == x && tabla[p][j].getIndex() == -1) 
							{
								tabla[i][j] = tabla[p][j];
								tabla[p][j] = igrac;
								break;
							}
							else throw new Exception("Neko je vec na tom polju!");
						}
				}
			}
		}
		else throw new Exception("Ne mozes tu da stanes!");
		
		postaviMod();
		if (isMica(x, y))
			mod = GameMode.MICA;
	}

	public boolean isMica(int x, int y)
	{
		Igrac igrac = tabla[x][y];
		boolean flag1;
		boolean flag2;
		
		if (x == 3)
		{
			flag1 = true;
			for (int i = 0; i < 7; i++)
				if (tabla[i][y] != null && tabla[i][y].getBoja() != igrac.getBoja())
				{
					flag1 = false;
					break;
				}
			
			if (y < 3)
			{				
				flag2 = true;
				for (int i = 0; i < 3; i++)
					if (tabla[x][i] != null && tabla[x][i].getBoja() != igrac.getBoja())
					{
						flag2 = false;
						break;
					}
				
			}
			else
			{
				flag2 = true;
				for (int i = 4; i < 7; i++)
					if (tabla[x][i] != null && tabla[x][i].getBoja() != igrac.getBoja())
					{
						flag2 = false;
						break;
					}
			}
		}
		else if (y == 3)
		{			
			flag1 = true;
			for (int i = 0; i < 7; i++)
				if (tabla[x][i] != null && tabla[x][i].getBoja() != igrac.getBoja())
				{
					flag1 = false;
					break;
				}
			
			if (x < 3)
			{				
				flag2 = true;
				for (int i = 0; i < 3; i++)
					if (tabla[i][y] != null && tabla[i][y].getBoja() != igrac.getBoja())
					{
						flag2 = false;
						break;
					}
				
			}
			else
			{
				flag2 = true;
				for (int i = 4; i < 7; i++)
					if (tabla[i][y] != null && tabla[i][y].getBoja() != igrac.getBoja())
					{
						flag2 = false;
						break;
					}
			}
		}
		else
		{
			flag1 = true;
			for (int i = 0; i < 7; i++)
				if (tabla[i][y] != null && tabla[i][y].getBoja() != igrac.getBoja())
				{
					flag1 = false;
					break;
				}
			
			flag2 = true;
			for (int i = 0; i < 7; i++)
				if (tabla[x][i] != null && tabla[x][i].getBoja() != igrac.getBoja())
				{
					flag2 = false;
					break;
				}
		}
		
		return flag1 | flag2;
	}	
	
	public boolean odrediMod()
	{
		boolean flag1 = true;
		boolean flag2 = true;
		
		for (int i = 0; i < 9; i++)
			if (beliIgrac[i] != null && beliIgrac[i].getStatus() == Status.POCETAK)
			{
				flag1 = false;
				break;
			}
		
		for (int i = 0; i < 9; i++)
			if (crveniIgrac[i] != null && crveniIgrac[i].getStatus() == Status.POCETAK)
			{
				flag2 = false;
				break;
			}
		
		if (flag1 && flag2)
			return true;
		
		return false;
	}
	
	public void postaviMod()
	{
		if (odrediMod())
			mod = GameMode.IGRANJE;
		else
			mod = GameMode.POSTAVLJANJE;
	}
	
	public String toString()
	{
		String s = "";
		
		for (int i = 0; i < 7; s += "\n", i++)
			for (int j = 0; j < 7; j++)
				if (tabla[i][j] == null) s += " . ";
				else if (tabla[i][j].getBoja() == Boja.BELA) s += " B ";
				else if (tabla[i][j].getBoja() == Boja.CRVENA) s += " C ";
				else s += " - ";
	
		return s;
	}



}
