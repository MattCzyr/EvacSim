package evac;

import java.time.Instant;

public class Main {
	
	public static Instant modelStart;
	public static Instant modelEnd;
	
	public static void main(String[] args) {
		// Disabled for testing
		/*if (args.length != 4) {
			System.out.println("Expects 3 arguments: nodes model filename, edges model filename, and hurricanes model filename");
			return;
		}
		String filename = args[1];*/
		
		// Hardcoded for testing
		String arg1 = "models/troy_model/main.csv";
		String arg2 = "models/troy_model/nodes.csv";
		String arg3 = "models/troy_model/edges.csv";
		String arg4 = "models/troy_model/hurricanes.csv";
		
		InputModel inputModel = new InputModel();
		
		Translator translator = new Translator(inputModel, arg1, arg2, arg3, arg4);
		
		if (!translator.translateMain()) {
			System.out.println("Error while translating main");
			return;
		}
		System.out.println("Finished translating main");
		
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
		
		// Start simulation
		OutputModel outputModel = NumberCruncher.crunch(inputModel);
		String kml = KmlGenerator.createKml(outputModel);
		System.out.println(kml);

	}
	
	public static void setModelStart(Instant instant) {
		modelStart = instant;
	}
	
	public static void setModelEnd(Instant instant) {
		modelEnd = instant;
	}
	
}
