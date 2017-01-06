package application.utils;

import java.util.HashMap;

public class Commande {
	
	public final static String PLAY_SCENARIO = "scenario";
	
	public static String execute(String commande, HashMap<String, String> params)
	{
		String result = "<div></div>";
		
		switch (commande)
		{
			case PLAY_SCENARIO:
				int numero = Integer.parseInt(params.get("num").trim());
				int partie = Integer.parseInt(params.get("part").trim());
				result = Scenario.play(numero, partie);
				break;
			default:
				System.err.println("Commande inconnue.");
				break;
			
		}
		
		return result;
	}

}
