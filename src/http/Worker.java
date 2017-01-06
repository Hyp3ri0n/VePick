
package http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import http.ResponseHTTP.ContentType;

/**
 * Class Server : A concurrent HTTP 1.1 server.
 * 
 */
public class Worker extends Thread
{
    private Socket clientSocket = null;
    private BufferedReader in = null;
    private OutputStream out = null;
    private final String DEFAULT_INDEX = "interfaces/index.html";

    public Worker(Socket socket) // make private => Server.getInstance()
    {
        clientSocket = socket;
    }

    @Override
    public void run()
    {
        System.out.println("[OPEN] Nouveau client connecté : " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

        try 
        {
            // Initialization            
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = clientSocket.getOutputStream();	// Pour l'envoi d'images dans la socket

            // Read header request
            String request = "", line = null;
            while ((line = in.readLine()) != null) 
            {
                //System.out.println(line);
                request = request.concat(line+"\r\n");
                if (line.isEmpty()) {
                	break;
                }
            }
            
            //Si la requéte est vide on arréte les traitements et on se remet é écouter sur le port
            if (request.isEmpty()) {
                System.err.println("Erreur de réception de la requéte client");
                return;
            }

            //Création de la requéte HTTP
            RequestHTTP req = new RequestHTTP(request);
            
            // Read body request
            StringBuilder body = new StringBuilder();
            if (req.getMethod().equals("POST")) {
                int c = 0;
                for (int i = 0; i < req.getContentLength(); i++) {
                    c = in.read();
                    body.append((char) c);
                }
            }
            
            // Set content of the request
            req.setContent(body.toString());

            // Vérification du protocole
            if(!req.getProtocol().equals(ResponseHTTP.HTTP1_1))
                System.err.println("Client using different protocol than server : " + req.getProtocol());

            // Handle request
            if (req.getMethod().equals(RequestHTTP.METHOD_GET)) {
                System.out.println("GET request");
            	
                // Handle GET response
            	ResponseHTTP response = new ResponseHTTP();
            	
            	if (req.getResource().equals(""))
            		req.setResource(DEFAULT_INDEX);
            	
        		// Gestion de la ressource voulue
                File resourceFile = new File(req.getResource());


                System.out.println("Response send");
                // Vérification que la ressource existe bien
                if (resourceFile.exists())
                {
                	// Vérification que la ressource peut étre lue
                	if (resourceFile.canRead())
                	{
                        // Get resource size for the buffer size
                        long dataSize = resourceFile.length();
                        byte[] data = new byte[(int)dataSize+1];
                        int dataReadSize;

                        FileInputStream fileReader = new FileInputStream(resourceFile);
                        do {
                            dataReadSize = fileReader.read(data);
                        } while(dataReadSize != -1);
                        fileReader.close();

                        String[] resourcePathArray = req.getResource().split("\\.");
                        String extension = resourcePathArray[resourcePathArray.length - 1];
                        
                        // On compléte la réponse avec les infos
                        response.setContentType(ContentType.getValueByExtension(extension));
                        response.setContent(data);
                	} else
                		response.setCode(ResponseHTTP.CODE_FORBIDDEN);
                } else
                	response.setCode(ResponseHTTP.CODE_NOT_FOUND);
            	
                // Send Server GET response
                out.write(response.toString().getBytes());
                out.write(response.getContent(), 0, response.getContent().length);
                out.flush();
                
            } else if (req.getMethod().equals(RequestHTTP.METHOD_POST)) {
                System.out.println("POST request");

                // Handle GET response
            	ResponseHTTP response = new ResponseHTTP();
            	
            	// Ressource de type java (donc demande d'objet) POST ?
            	if (req.getResource().startsWith("java/"))
            	{
            		Socket appSocket = new Socket("127.0.0.1", 9898);
                    BufferedReader appIn = new BufferedReader(new InputStreamReader(appSocket.getInputStream()));
                    BufferedWriter appOut = new BufferedWriter(new OutputStreamWriter(appSocket.getOutputStream()));

                    // Send param to the application
                    for (String param : req.getParams())
                    	appOut.write(param + "&");
                    appOut.write('\0');
                    appOut.flush();
                    
                    // Read body request
                    StringBuilder bodyReq = new StringBuilder();
                    int ch ;
    				while ((ch = appIn.read()) != -1) {
    					if (ch == 0) 
    						break;
    					
    					bodyReq.append((char) ch);
    				}
                    
                    response.setContent(bodyReq.toString().getBytes());
                 
                    // Send Server POST response
                    out.write(response.toString().getBytes());
                    out.write(response.getContent());
                    out.flush();
                    
                    appIn.close();
                    appOut.close();
                    appSocket.close();
            	}
            }

            // Dans tous les cas on ferme les flux car traitement de requête + réponse au client fait.
            System.out.println("[CLOSE] Connexion avec le client terminée\n");
            out.close();
            in.close();
            clientSocket.close();
            
        } 
        catch (Exception e) 
        {
            System.err.println("Erreur lors de la Connexion avec le client." + e.getMessage());
            e.printStackTrace();
        }
    }
}
