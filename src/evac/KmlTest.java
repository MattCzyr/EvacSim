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
		
		List<HurricaneData> hurricaneSightings = new ArrayList<>();
		hurricaneSightings.add(new HurricaneData(new Position(-1, -5), new Velocity(280, 25), Instant.now(), 55, 80, 7));
		hurricaneSightings.add(new HurricaneData(new Position(-2.5, -4), new Velocity(350, 20), Instant.now(), 50, 70, 7));
		hurricaneSightings.add(new HurricaneData(new Position(0, -1.5), new Velocity(0, 22.5), Instant.now(), 53, 95, 7));
		
		List<DangerZone> dangerZones = new ArrayList<>();
		dangerZones.add(new DangerZone(new Polygon(dangerZone1Points), 2, Instant.now()));
		dangerZones.add(new DangerZone(new Polygon(dangerZone2Points), 3, Instant.now()));
		
		Hurricane hurricane = new Hurricane(hurricaneSightings, dangerZones);
		
		List<Hurricane> hurricanes = new ArrayList<>();
		hurricanes.add(hurricane);
		
		List<Node> nodes = new ArrayList<>();
		nodes.add(new Node(3000, 7000, new Position(0.5, 0.5)));
		nodes.add(new Node(100000, 120000, new Position(0.5, 1)));
		nodes.add(new Node(50000, 100000, new Position(0, 1.5)));
		
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
