package evac;

public class Main {
	
	public static void main(String[] args) {
		// Disabled for testing
		/*if (args.length != 4) {
			System.out.println("Expects 3 arguments: nodes model filename, edges model filename, and hurricanes model filename");
			return;
		}
		String filename = args[1];*/
		
		// Hardcoded for testing
		String arg1 = "models/troy_model/nodes.csv";
		String arg2 = "models/troy_model/edges.csv";
		String arg3 = "models/troy_model/hurricanes.csv";
		
		InputModel model = new InputModel();
		
		Translator translator = new Translator(model, arg1, arg2, arg3);
		
		if (!translator.translateNodes()) {
			System.out.println("Error while translating nodes");
			return;
		}
		System.out.println("Finished translating nodes");
		
		if (!translator.translateEdges()) {
			System.out.println("Error while translating edges");
			return;
		}
		System.out.println("Finished translating edges");
		
		if (!translator.translateHurricanes()) {
			System.out.println("Error while translating hurricanes");
		}
		System.out.println("Finished translating hurricanes");
		
		// TODO: Start simulation
	}
}
