package evac;

public class Main {
	
	public static void main(String[] args) {
		InputModel inputModel = null;
		OutputModel outputModel = NumberCruncher.crunch(inputModel);
		String kml = KmlGenerator.createKml(outputModel);
		System.out.println(kml);
	}
}
