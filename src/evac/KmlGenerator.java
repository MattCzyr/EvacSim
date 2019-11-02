package evac;

import java.util.List;

public class KmlGenerator {
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	private static final String HEADER = "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n";
	
	private static final String FOOTER = "</kml>\n";
	
	public static String createKml(OutputModel model) {
		return null; // convert the model to KML that can be displayed in Google Earth
	}
	
	public static String createKml(Position position) {
		StringBuilder builder = new StringBuilder();
		builder.append(XML_HEADER);
		builder.append(HEADER);
		
		builder.append("<Placemark>\n");
		builder.append("  <name>Position Placemark</name>\n");
		builder.append("  <description>This is a position</description>\n");
		builder.append("  <Point>\n");
		builder.append(String.format("    <coordinates>%f,%f</coordinates>\n", position.latitude, position.longitude));
		builder.append("  </Point>\n");
		builder.append("</Placemark>\n");
		
		builder.append(FOOTER);
		return builder.toString();
	}
	
	public static String createKml(Polygon polygon) {
		StringBuilder builder = new StringBuilder();
		builder.append(XML_HEADER);
		builder.append(HEADER);
		
		builder.append("<Placemark>\n");
		builder.append("  <name>Polygon Placemark</name>\n");
		builder.append("  <description>This is a polygon</description>\n");
		builder.append("  <Polygon>\n");
		builder.append("    <altitudeMode>clampToGround</altitudeMode>\n");
		builder.append("    <outerBoundaryIs><LinearRing><coordinates>\n");
		for (Position position : polygon.positions) {
			builder.append(String.format("      %f,%f\n", position.latitude, position.longitude));
		}
		builder.append("    </coordinates></LinearRing></outerBoundaryIs>\n");
		builder.append("  </Polygon>\n");
		builder.append("  <Style><PolyStyle><color>#a00000ff</color><outline>0</outline></PolyStyle></Style>\n");
		builder.append("</Placemark>\n");
		
		builder.append(FOOTER);
		return builder.toString();
	}
	
	public static String createKmlLineString(List<Position> points) {
		StringBuilder builder = new StringBuilder();
		builder.append(XML_HEADER);
		builder.append(HEADER);
		
		builder.append("<Placemark>\n");
		builder.append("  <name>Line String Placemark</name>\n");
		builder.append("  <description>This is a line string</description>\n");
		builder.append("  <LineString>\n");
		builder.append("    <altitudeMode>clampToGround</altitudeMode>\n");
		builder.append("    <coordinates>\n");
		for (Position point : points) {
			builder.append(String.format("      %f,%f\n", point.latitude, point.longitude));
		}
		builder.append("    </coordinates>\n");
		builder.append("  </LineString>\n");
		builder.append("  <Style><LineStyle><color>7f0000ff</color><width>4</width></LineStyle></Style>\n");
		builder.append("</Placemark>\n");
		
		builder.append(FOOTER);
		return builder.toString();
	}
}
