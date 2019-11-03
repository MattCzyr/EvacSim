package evac;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class KmlMain {
	public static void main(String[] args) {
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
		
		List<Node> evac = new ArrayList<>();
		evac.add(inputModel.nodes.get("Troy")); // troy
		evac.add(inputModel.nodes.get("Brunswick")); // brunswick
		
		List<Node> dest = new ArrayList<>();
		dest.add(inputModel.nodes.get("Albany"));
		dest.add(inputModel.nodes.get("Guilderland"));
		dest.add(inputModel.nodes.get("EastGreenbush"));
		dest.add(inputModel.nodes.get("Schenectady"));
		
		String flowsFilename = "data/flows.csv";
		CSVParser csvParser = null;
		try {
			csvParser = CSVFormat.DEFAULT.parse(new FileReader(new File(flowsFilename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		List<SolvedEdge> solvedFlows = new ArrayList<>();
		for (CSVRecord record : csvParser) {
			String src = record.get(0);
			String dst = record.get(1);
			float flow = Float.valueOf(record.get(2));
			for (Edge edge : inputModel.edges) {
				if (edge.src.name.contentEquals(src) && edge.dst.name.contentEquals(dst)) {
					if (flow > 0) {
						solvedFlows.add(new SolvedEdge(edge, (int)flow));
					}
					break;
				}
			}
		}
		
		List<Node> nodes = new ArrayList<>(inputModel.nodes.values());
		
		List<Position> dangerZone1Positions = new ArrayList<>();
		
		dangerZone1Positions.add(new Position(42.0 + (44.0 / 60.0), -73.0 - (44.0 / 60.0)));
		dangerZone1Positions.add(new Position(42.0 + (42.0 / 60.0), -73.0 - (43.0 / 60.0)));
		dangerZone1Positions.add(new Position(42.0 + (42.0 / 60.0), -73.0 - (39.0 / 60.0)));
		dangerZone1Positions.add(new Position(42.0 + (47.0 / 60.0), -73.0 - (36.0 / 60.0)));
		dangerZone1Positions.add(new Position(42.0 + (52.0 / 60.0), -73.0 - (41.0 / 60.0)));
		
		List<Position> dangerZone2Positions = new ArrayList<>();
		dangerZone2Positions.add(new Position(42.0 + (42.0 / 60.0), -73.0 - (39.0 / 60.0)));
		dangerZone2Positions.add(new Position(42.0 + (38.0 / 60.0), -73.0 - (36.0 / 60.0)));
		dangerZone2Positions.add(new Position(42.0 + (42.0 / 60.0), -73.0 - (29.0 / 60.0)));
		dangerZone2Positions.add(new Position(42.0 + (46.0 / 60.0), -73.0 - (32.0 / 60.0)));
		dangerZone2Positions.add(new Position(42.0 + (47.0 / 60.0), -73.0 - (36.0 / 60.0)));
		
		DangerZone dangerZone1 = new DangerZone(new Polygon(dangerZone1Positions), 2, Instant.now());
		DangerZone dangerZone2 = new DangerZone(new Polygon(dangerZone2Positions), 3, Instant.now());
		
		inputModel.hurricanes.get(0).dangerZones.add(dangerZone1);
		inputModel.hurricanes.get(0).dangerZones.add(dangerZone2);
		
		OutputModel output = new OutputModel(inputModel.hurricanes, solvedFlows, nodes);
		String kml = KmlGenerator.createKml(output);
		
		File kmlDataFile = new File("./data/output.kml");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(kmlDataFile))) {
			writer.write(kml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("data output:");
		System.out.println(kml);
	}
}
