package http;

public class ResponseHTTP 
{
	public enum ContentType {
		
		TEXT_HTML(new String[]{"html"}, "text/html"),
		TEXT_PLAIN(new String[]{"txt"}, "text/plain"),
		IMAGE_JPG(new String[]{"jpg", "jpeg"}, "image/jpg"),
		IMAGE_PNG(new String[]{"png"}, "image/png");
		
		
		private String[] extension;
		private String value;
		
		private ContentType(String[] extension, String value)
		{
			this.extension = extension;
			this.value = value;
		}
		
		public static String getValueByExtension(String extension)
		{
			if (extension == null || extension.equals("") || extension.equals("."))
				return "text/html";
			
			if (extension.getBytes()[0] == '.')
				extension = extension.substring(1);
			
			for (ContentType ct : ContentType.values())
				for (String ext : ct.extension)
					if (ext.equals(extension))
						return ct.value;
			
			return "text/html";
		}
	}
	
    public static final String HTTP1_1 = "HTTP/1.1";
    public static final String CODE_OK = "200 OK";
    public static final String CODE_NOT_FOUND = "404 Not Found";
    public static final String CODE_FORBIDDEN = "403 Forbidden";
    
	private String code = "";
	private String contentType = "";
	private byte[] content = new byte[]{};
	
	/**
	 * Permet de créer une réponse http
	 */
	public ResponseHTTP() 
	{
        this.code = CODE_OK;
        this.contentType = "text/html";
	}

	public void setCode(String newCode) {	this.code = newCode;	}
	public String getCode() {	return this.code;	}

	public void setContentType(String newContentType) { this.contentType = newContentType; }
	public String getContentType() { return this.contentType; }

	public void setContent(byte[] newcontent) { this.content = newcontent; }
	public byte[] getContent() { return this.content; }
	
	/**
	 * Header
	 */
	@Override
	public String toString() {
		String s = "";
		
		s += HTTP1_1 + " " + this.code + "\r\n";
		s += "Content-Type: " + this.contentType + "\r\n";
		s += "Content-Length: " + this.content.length + "\r\n";
		s += "\r\n";
		
		return s;
	}
	
}
