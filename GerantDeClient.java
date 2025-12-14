import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GerantDeClient implements Runnable
{
	private Socket sClient;
	private ArrayList<Client> lstClientPartage = new ArrayList<Client>();
	private String nom;

	public GerantDeClient(Socket sClient, ArrayList<Client> lstClient) 
	{
		this.sClient = sClient;
		this.lstClientPartage = lstClient;
		this.nom = "";
	}

	public String getNom() { return this.nom; } // retorune le nom du client

	// supprime le client ( notamment utilisé lorsque le client quitte le canal de discussion )
	public void supprimerClient(Client client)
	{
		if (client == null) return; // vérifie que le client n'est pas null

		this.lstClientPartage.remove(client); // supprimmer de la liste le client
	}

	public void afficherMessage(String msg, PrintWriter out) // permet d'afficher un message qu'un client a entrer
	{
		if (out != null) 
		{
			out.print("\r");
			out.print(" ".repeat(80)); 
			out.print("\r");
			out.println(msg);
			out.print("[ Vous ] : ");
			out.flush();
		}
	}

	public void run() // fonction de lancement
	{
		String message = "";
		System.out.println("Un nouveau client s'est connecte");
		Client leClient = null;
		try
		{
			Boolean boK = true; // valeur booleenne permettant de continuer l'ouverture du serveur tant qu'il est ouvert ( on peut l'arréter avec la variable a false )
			BufferedReader in = new BufferedReader(new InputStreamReader(sClient.getInputStream()));
			PrintWriter out = new PrintWriter(sClient.getOutputStream(), true);

			out.println("Bienvenue sur le serveur de Paul");
			out.print("Entrer un pseudo : ");
			out.flush();
			this.nom = in.readLine(); // lit le premier message qui est attribué comme pseudo pour le tchat

			leClient = new Client(out, this.nom);
			this.lstClientPartage.add(leClient); // ajoute le nouveau client dans la liste

			while( boK )
			{
				out.print("[ Vous ] : "); // permet l'affichage constant et propre
				out.flush();
				message = in.readLine(); // permet de lire le message envoyer par l'utilisateur
				if (message == null)
				{
					String msgQuit = "[Serveur] " + nom + " a quitte le chat.";
					for (Client c : lstClientPartage)
					{
						if (c != leClient)
						{
							// affiche le message de fin de session pour le client ayant quitté
							afficherMessage(msgQuit, c.getOut());
						}
					}
					break;
				}
				
				// Si l'utilisateur tape juste Entrée ou des espaces, on ignore
				if (message.trim().isEmpty())
					continue;

				// affichage propre du nom + le message
				String messageComplet = "[ " + this.nom + " ] : " + message;
				for (Client c : lstClientPartage)
				{
					if (c.getOut() != out)
					{
						// permet d'afficher le message aux autres utilisateurs (sauf à lui meme)
						afficherMessage(messageComplet, c.getOut());
					}
				}
				boK = !out.checkError();
			}
			System.out.println("Client deconnecter");
			sClient.close();
		}
		catch (Exception ie)
		{
			ie.printStackTrace();
			this.supprimerClient(leClient);
		}
	}
}