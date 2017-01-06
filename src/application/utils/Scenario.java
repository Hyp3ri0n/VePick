package application.utils;

public class Scenario 
{

	public static String play(int numero, int partie) 
	{
		String result = "";
		switch (numero)
		{
			case 1:
				result = premierScenario(partie);
				break;
			case 2:
				
				break;
			default:
				System.err.println("Numéro de scénario inconnu.");
				break;
		}
		return result;
	}
	
	
	private static String premierScenario(int partie)
	{
		String result = "";
		switch (partie)
		{
			case 1:
				result = "<div>Partie 1 : DONE</div>";
				break;
			case 2:
				result = "<div>Partie 2 : DONE</div>";
				break;
			default:
				System.err.println("Partie de scénario inconnu.");
				break;
		}
		return result;
		
	}

}
