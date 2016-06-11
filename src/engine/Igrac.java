package engine;

public class Igrac
{
	private int index;
	private Boja boja;
	private String image;
	private Status status;
	
	public Igrac(int index, Boja boja, String image, Status status)
	{
		this.index = index;
		this.boja = boja;
		this.image = image;
		this.status = status;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public Boja getBoja()
	{
		return boja;
	}

	public String getImage()
	{
		return image;
	}
	
	public int getIndex()
	{
		return index;
	}
	
}
