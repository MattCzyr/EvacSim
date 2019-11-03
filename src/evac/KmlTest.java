package evac;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class KmlTest {
	public static void main(String[] args) {
		String kml;
		
		List<Position> dangerZone1Points = new ArrayList<>();
		dangerZone1Points.add(new Position(-1, -1));
		dangerZone1Points.add(new Position(1, -1));
		dangerZone1Points.add(new Position(2, 0.5));
		dangerZone1Points.add(new Position(1, 1));
		dangerZone1Points.add(new Position(-1, 1));
		dangerZone1Points.add(new Position(-2, 0.5));
		List<Position> dangerZone2Points = new ArrayList<>();
		dangerZone2Points.add(new Position(-1, -1));
		dangerZone2Points.add(new Position(-0.875, -2));
		dangerZone2Points.add(new Position(-0.5, -2.5));
		dangerZone2Points.add(new Position(0.5, -2.5));
		dangerZone2Points.add(new Position(0.875, -2));
		dangerZone2Points.add(new Position(1, -1));
		
		Hurricane hurricane = new Hurricane("Hurricane1", new Position(-1, -5), new Velocity(280, 25), 80, 125);
		hurricane.data.add(new HurricaneData(new Position(-2.5, -4), new Velocity(350, 20), Instant.now(), 100));
		hurricane.data.add(new HurricaneData(new Position(0, -1.5), new Velocity(0, 22.5), Instant.now(), 125));
		
		hurricane.dangerZones.add(new DangerZone(new Polygon(dangerZone1Points), 2, Instant.now()));
		hurricane.dangerZones.add(new DangerZone(new Polygon(dangerZone2Points), 3, Instant.now()));
		
		List<Hurricane> hurricanes = new ArrayList<>();
		hurricanes.add(hurricane);
		
		List<Node> nodes = new ArrayList<>();
		nodes.add(new Node("A", 3000, 7000, new Position(0.5, 0.5)));
		nodes.add(new Node("B", 100000, 120000, new Position(0.5, 1)));
		nodes.add(new Node("C", 50000, 100000, new Position(0, 1.5)));
		
		List<SolvedEdge> edges = new ArrayList<>();
		edges.add(new SolvedEdge(new Edge(nodes.get(0), nodes.get(1), 7, 20000), 20000));
		edges.add(new SolvedEdge(new Edge(nodes.get(1), nodes.get(2), 7, 10000), 5000));
		edges.add(new SolvedEdge(new Edge(nodes.get(2), nodes.get(1), 7, 30000), 25000));
		edges.add(new SolvedEdge(new Edge(nodes.get(2), nodes.get(0), 7, 40000), 28000));
		
		OutputModel output = new OutputModel(hurricanes, edges, nodes);
		
		kml = KmlGenerator.createKml(output);
		System.out.print(kml);
	}
}
