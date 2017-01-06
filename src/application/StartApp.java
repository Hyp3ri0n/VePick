package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import application.utils.Commande;

public class StartApp extends Thread {
	
	
	@Override
	public void run() 
	{
		
		// Création des objets java
		
		
		// Ecoute sur un certain port
		ServerSocket serverSocket = null;
		int serverPort = 9898;
        try 
        {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Application is runnig on port : " + serverPort);

            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                
                System.out.println("[OPEN] Nouvelle demande (application) de : " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                
                // Read body request
                StringBuilder body = new StringBuilder();
                int ch ;
				while ((ch = in.read()) != -1) {
					if (ch == 0) 
					{
						body = new StringBuilder(body.substring(0, body.length() - 1));
						break;
					}
					body.append((char) ch);
				}
                
                // Traitement de la demande
                System.out.println("Traitement de la demande en cours");
                String response = getResponse(body.toString());
        		
                out.write(response);
                out.write('\0');
                out.flush();
                
                // Dans tous les cas on ferme les flux car traitement de requête + réponse au client fait.
                System.out.println("[CLOSE] Connexion avec l'application terminée");
                out.close();
                in.close();
                clientSocket.close();
            }

        }
        catch (Exception e) 
        {
            System.err.println("Erreur lors du lancement de l'application.\n" + e.getMessage());
            e.printStackTrace();
        }
		
	}
	
	private String getResponse(String params)
	{
		// Separate params
		HashMap<String, String> paramsMap = new HashMap<>();
		for (String block : params.split("&"))
		{
			String[] temp = block.split("=");
			paramsMap.put(temp[0], temp[1]);
		}
		
		return Commande.execute(paramsMap.get("commande"), paramsMap);
		
	}

}
