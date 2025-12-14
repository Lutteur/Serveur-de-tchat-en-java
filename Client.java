import java.io.PrintWriter;

public class Client
{
	private PrintWriter out;
	private String nom;

	public Client(PrintWriter out, String nom)
	{
		this.out = out;
		this.nom = nom;
	}

	public PrintWriter getOut()
	{
		return this.out;
	}

	public String getNom()
	{
		return this.nom;
	}
}