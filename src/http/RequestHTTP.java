package http;

import java.util.Arrays;

public class RequestHTTP 
{
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    
	private String method = "";
	private String resource = "";
	private String[] params = new String[]{};
	private String protocol = "";
	private String connection = "";
	private int contentLength = 0;
	private String contentType = "";
	private String acceptEncoding = "";
	private String acceptLanguage = "";
	private String content = "";
	
	/**
	 * Permet de créer une requéte reéue par la socket
	 * @param request La String reéue
	 */
	public RequestHTTP(String request) 
	{
        // Get request as an array
        String[] requestArray = request.split("\r\n");

        // Get first line of type "GET resourceRelativePath HTTP/1.1"
        String[] requestLine = requestArray[0].split(" ");
        this.method = requestLine[0].trim();
        this.resource = requestLine[1].trim();
        this.resource = (resource.charAt(0) == '/') ? resource.substring(1) : resource;
        
        // Gestion des params
        if (this.method.equals("GET"))
        {
	        if (this.resource.contains("?"))
	        {
	        	String[] arrayRes = this.resource.split("\\?");
	        	this.resource = arrayRes[0];
	  
	        	this.params = Arrays.copyOfRange(arrayRes, 1, arrayRes.length);        	
	        }
        }

        // Check client and server are using same HTTP protocol version
        this.protocol = requestLine[2];
        
        
        // Check headers : inutile
        // TODO : Manque des headers
        for (int i = 1; i < requestArray.length; i++) 
        {
            String[] headerArray = requestArray[i].split(":");
            String headerName = headerArray[0].trim();
            String headerValue = headerArray[1].trim();

            switch (headerName) {
                case "Connection":
                	this.connection = headerValue;
                	break;
                case "Content-Length":
                	this.contentLength = Integer.valueOf(headerValue);
                    break;
                case "Content-Type":
                	this.contentType = headerValue;
                    break;
                case "Accept-Encoding":
                	this.acceptEncoding = headerValue;
                    break;
                case "Accept-Language":
                	this.acceptLanguage = headerValue;
                    break;
                default:
                    //System.err.println("Unknown header : " + headerName + headerValue);
                    break;
            }
        }
	}

	public String getMethod() {	return this.method;	}

	public String getResource() { return this.resource; }

	public void setResource(String newResource) { this.resource = newResource; }

	public String[] getParams() { return this.params; }

	public String getProtocol() { return this.protocol; }

	public String getConnection() {	return this.connection;	}

	public int getContentLength() { return this.contentLength; }

	public String getContentType() { return this.contentType; }

	public String getAcceptEncoding() { return this.acceptEncoding; }

	public String getAcceptLanguage() { return this.acceptLanguage; }

	public String getContent() { return this.content; }

	public void setContent(String newContent) 
	{
		this.content = newContent;
		
        // Gestion du content de la request (param POST)
        if (this.getMethod().equals("POST"))
        {
        	this.params = newContent.split("&");
        }
	}
}
