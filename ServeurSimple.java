import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;

public class ServeurSimple
{
	public ServeurSimple()
	{
		System.out.println("attente d'un client...");
		ArrayList<Client>  lstClient = new ArrayList<Client>();
		try (ServerSocket ss = new ServerSocket(6000))
		{
			while (true)
			{
				// attendre patiemment un client
				Socket s = ss.accept();
				// créer un GerantDeClient pour traiter ce nouveau client
				GerantDeClient gdc = new GerantDeClient(s, lstClient);
				// mettre ce gérant de client dans une Thread
				Thread tgdc = new Thread(gdc);
				// lancer la thread qui gérera ce client
				tgdc.start();
				// ... et on recommence...
			}
		} catch (IOException e)
		{
			System.out.println("Impossible de creer");
		}

		System.out.println("Bienvenue sur ServeurSimple1 de Paul");

	}
}
