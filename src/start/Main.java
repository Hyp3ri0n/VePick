package start;

import java.net.ServerSocket;
import java.net.Socket;

import application.StartApp;
import http.Worker;

public class Main {

	public static void main(String[] args) {
		// Thread Socket Java des objets
		new StartApp().start();
		
		
		// Lancement du serveur HTTP
		ServerSocket serverSocket = null;
		int serverPort = 4545;
        try 
        {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server HTTP is runnig on port : " + serverPort + "\n");

            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                new Worker(clientSocket).start();
            }

        }
        catch (Exception e) 
        {
            System.err.println("Erreur lors du lancement du serveur HTTP.\n" + e.getMessage());
        }
	}

}
