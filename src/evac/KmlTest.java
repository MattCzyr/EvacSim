package evac;

import java.util.ArrayList;
import java.util.List;

public class KmlTest {
	public static void main(String[] args) {
		String kml;
		
		//Position position = new Position(0, 0);
		
//		kml = KmlGenerator.createKml(position);
//		System.out.print(kml);
		
		List<Position> polygonPoints = new ArrayList<Position>();
		polygonPoints.add(new Position(-1, -1));
		polygonPoints.add(new Position(1, -1));
		polygonPoints.add(new Position(2, 0));
		polygonPoints.add(new Position(1, 1));
		polygonPoints.add(new Position(-1, 1));
		polygonPoints.add(new Position(-2, 0));
		Polygon polygon = new Polygon(polygonPoints);
		
//		kml = KmlGenerator.createKml(polygon);
//		System.out.print(kml);
		
		kml = KmlGenerator.createKmlLineString(polygonPoints);
		System.out.print(kml);
	}
}
