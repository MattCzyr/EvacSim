package evac;

import java.time.ZoneId;
import java.util.List;

public class KmlGenerator {
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	private static final String HEADER = "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n";
	
	private static final String FOOTER = "</kml>\n";
	
	public static String createKml(OutputModel model) {
		StringBuilder builder = new StringBuilder();
		builder.append(XML_HEADER);
		builder.append(HEADER);
		
		builder.append("<Document>\n");
		builder.append("  <name>Output Model</name>\n");
		
		for (Hurricane hurricane : model.hurricanes) {
			addHurricane(builder, hurricane);
		}
		
		addNodes(builder, model.nodes);
		
		addEdges(builder, model.flows);
		
		builder.append("</Document>\n");
		
		builder.append(FOOTER);
		return builder.toString();
	}
	
	public static void addEdges(StringBuilder builder, List<SolvedEdge> edges) {
		builder.append("<Folder>\n");
		builder.append("  <name>Edges</name>\n");
		
		for (SolvedEdge edge : edges) {
			builder.append("<Placemark>\n");
			builder.append("  <name>Node</name>\n");
			builder.append("  <description><![CDATA[\n");
			builder.append("  <table>\n");
			builder.append(String.format("  <tr><td>Src Position</td><td>%f, %f</td></tr>\n", edge.edge.src.position.latitude, edge.edge.src.position.longitude));
			builder.append(String.format("  <tr><td>Dst Position</td><td>%f, %f</td></tr>\n", edge.edge.dst.position.latitude, edge.edge.dst.position.longitude));
			builder.append(String.format("  <tr><td>Flow</td><td>%d</td></tr>\n", edge.flow));
			builder.append("  </table>\n");
			builder.append("  ]]></description>\n");
			builder.append("  <LineString>\n");
			builder.append("    <coordinates>\n");
			builder.append(String.format("      %f,%f\n", edge.edge.src.position.latitude, edge.edge.src.position.longitude));
			builder.append(String.format("      %f,%f\n", edge.edge.dst.position.latitude, edge.edge.dst.position.longitude));
			builder.append("    </coordinates>\n");
			builder.append("  </LineString>\n");
			builder.append("</Placemark>\n");
		}
		
		builder.append("</Folder>\n");
	}
	
	public static void addNodes(StringBuilder builder, List<Node> nodes) {
		builder.append("<Folder>\n");
		builder.append("  <name>Nodes</name>\n");
		
		for (Node node : nodes) {
			builder.append("<Placemark>\n");
			builder.append("  <name>Node</name>\n");
			builder.append("  <description><![CDATA[\n");
			builder.append("  <table>\n");
			builder.append(String.format("  <tr><td>Position</td><td>%f, %f</td></tr>\n", node.position.latitude, node.position.longitude));
			builder.append(String.format("  <tr><td>Population</td><td>%d</td></tr>\n", node.population));
			builder.append("  </table>\n");
			builder.append("  ]]></description>\n");
			builder.append("  <Point>\n");
			builder.append(String.format("    <coordinates>%f,%f</coordinates>\n", node.position.latitude, node.position.longitude));
			builder.append("  </Point>\n");
			builder.append("</Placemark>\n");
		}
		
		builder.append("</Folder>\n");
	}
	
	public static void addHurricane(StringBuilder builder, Hurricane hurricane) {
		builder.append("<Folder>\n");
		builder.append("  <name>Hurricane</name>\n");
		
		// Hurricane Danger Zones
		for (DangerZone dangerZone : hurricane.dangerZones) {
			builder.append("<Placemark>\n");
			builder.append(String.format("  <name>Danger Zone (level %d)</name>\n", dangerZone.dangerLevel));
			builder.append(String.format("  <description>%s</description>\n", dangerZone.time.atZone(ZoneId.of("America/New_York"))));
			builder.append("  <Polygon>\n");
			builder.append("    <altitudeMode>clampToGround</altitudeMode>\n");
			builder.append("    <outerBoundaryIs><LinearRing><coordinates>\n");
			for (Position position : dangerZone.polygon.positions) {
				builder.append(String.format("      %f,%f\n", position.latitude, position.longitude));
			}
			builder.append("    </coordinates></LinearRing></outerBoundaryIs>\n");
			builder.append("  </Polygon>\n");
			builder.append("  <Style><PolyStyle><color>#a00000ff</color><outline>0</outline></PolyStyle></Style>\n");
			builder.append("</Placemark>\n");
		}
		
		// Hurricane Path
		builder.append("<Placemark>\n");
		builder.append("  <name>Hurricane Path</name>\n");
		builder.append("  <LineString>\n");
		builder.append("    <altitudeMode>clampToGround</altitudeMode>\n");
		builder.append("    <coordinates>\n");
		for (HurricaneData hurricaneData : hurricane.data) {
			builder.append(String.format("      %f,%f\n", hurricaneData.position.latitude, hurricaneData.position.longitude));
		}
		builder.append("    </coordinates>\n");
		builder.append("  </LineString>\n");
		builder.append("  <Style><LineStyle><color>#ff0000ff</color><width>2</width></LineStyle></Style>\n");
		builder.append("</Placemark>\n");
		
		for (HurricaneData hurricaneData : hurricane.data) {
			builder.append("<Placemark>\n");
			builder.append("  <name>Hurricane Measurement</name>\n");
			builder.append("  <description><![CDATA[\n");
			builder.append("  <table>\n");
			builder.append(String.format("  <tr><td>Position</td><td>%f, %f</td></tr>\n", hurricaneData.position.latitude, hurricaneData.position.longitude));
			builder.append(String.format("  <tr><td>Direction</td><td>%f\u00b0 @ %f mph</td></tr>\n", hurricaneData.velocity.inclination, hurricaneData.velocity.speed));
			builder.append(String.format("  <tr><td>Wind Speed</td><td>%f mph</td></tr>\n", hurricaneData.wind));
			builder.append(String.format("  <tr><td>Wind Gusts</td><td>%f mph</td></tr>\n", hurricaneData.gusts));
			builder.append(String.format("  <tr><td>Category</td><td>%d</td></tr>\n", hurricaneData.category));
			builder.append("  </table>\n");
			builder.append("  ]]></description>\n");
			builder.append("  <Point>\n");
			builder.append(String.format("    <coordinates>%f,%f</coordinates>\n", hurricaneData.position.latitude, hurricaneData.position.longitude));
			builder.append("  </Point>\n");
			builder.append("</Placemark>\n");
		}
		
		builder.append("</Folder>\n");
	}
	
	public static String createKml(Hurricane hurricane) {
		StringBuilder builder = new StringBuilder();
		builder.append(XML_HEADER);
		builder.append(HEADER);
		
		builder.append("<Folder>\n");
		builder.append("  <name>Hurricane</name>\n");
		
		// Hurricane Danger Zones
		for (DangerZone dangerZone : hurricane.dangerZones) {
			builder.append("<Placemark>\n");
			builder.append(String.format("  <name>Danger Zone (level %d)</name>\n", dangerZone.dangerLevel));
			builder.append(String.format("  <description>%s</description>\n", dangerZone.time.atZone(ZoneId.of("America/New_York"))));
			builder.append("  <Polygon>\n");
			builder.append("    <altitudeMode>clampToGround</altitudeMode>\n");
			builder.append("    <outerBoundaryIs><LinearRing><coordinates>\n");
			for (Position position : dangerZone.polygon.positions) {
				builder.append(String.format("      %f,%f\n", position.latitude, position.longitude));
			}
			builder.append("    </coordinates></LinearRing></outerBoundaryIs>\n");
			builder.append("  </Polygon>\n");
			builder.append("  <Style><PolyStyle><color>#a00000ff</color><outline>0</outline></PolyStyle></Style>\n");
			builder.append("</Placemark>\n");
		}
		
		// Hurricane Path
		builder.append("<Placemark>\n");
		builder.append("  <name>Hurricane Path</name>\n");
		builder.append("  <LineString>\n");
		builder.append("    <altitudeMode>clampToGround</altitudeMode>\n");
		builder.append("    <coordinates>\n");
		for (HurricaneData hurricaneData : hurricane.data) {
			builder.append(String.format("      %f,%f\n", hurricaneData.position.latitude, hurricaneData.position.longitude));
		}
		builder.append("    </coordinates>\n");
		builder.append("  </LineString>\n");
		builder.append("  <Style><LineStyle><color>#ff0000ff</color><width>2</width></LineStyle></Style>\n");
		builder.append("</Placemark>\n");
		
		for (HurricaneData hurricaneData : hurricane.data) {
			builder.append("<Placemark>\n");
			builder.append("  <name>Hurricane Measurement</name>\n");
			builder.append("  <description><![CDATA[\n");
			builder.append("  <table>\n");
			builder.append(String.format("  <tr><td>Position</td><td>%f, %f</td></tr>\n", hurricaneData.position.latitude, hurricaneData.position.longitude));
			builder.append(String.format("  <tr><td>Direction</td><td>%f\u00b0 @ %f mph</td></tr>\n", hurricaneData.velocity.inclination, hurricaneData.velocity.speed));
			builder.append(String.format("  <tr><td>Wind Speed</td><td>%f mph</td></tr>\n", hurricaneData.wind));
			builder.append(String.format("  <tr><td>Wind Gusts</td><td>%f mph</td></tr>\n", hurricaneData.gusts));
			builder.append(String.format("  <tr><td>Category</td><td>%d</td></tr>\n", hurricaneData.category));
			builder.append("  </table>\n");
			builder.append("  ]]></description>\n");
			builder.append("  <Point>\n");
			builder.append(String.format("    <coordinates>%f,%f</coordinates>\n", hurricaneData.position.latitude, hurricaneData.position.longitude));
			builder.append("  </Point>\n");
			builder.append("</Placemark>\n");
		}
		
		builder.append("</Folder>\n");
		
		builder.append(FOOTER);
		return builder.toString();
	}
	
	public static String createKml(DangerZone dangerZone) {
		StringBuilder builder = new StringBuilder();
		builder.append(XML_HEADER);
		builder.append(HEADER);
		
		builder.append("<Placemark>\n");
		builder.append(String.format("  <name>Danger Zone (level %d)</name>\n", dangerZone.dangerLevel));
		builder.append(String.format("  <description>%s</description>\n", dangerZone.time.atZone(ZoneId.of("America/New_York"))));
		builder.append("  <Polygon>\n");
		builder.append("    <altitudeMode>clampToGround</altitudeMode>\n");
		builder.append("    <outerBoundaryIs><LinearRing><coordinates>\n");
		for (Position position : dangerZone.polygon.positions) {
			builder.append(String.format("      %f,%f\n", position.latitude, position.longitude));
		}
		builder.append("    </coordinates></LinearRing></outerBoundaryIs>\n");
		builder.append("  </Polygon>\n");
		builder.append("  <Style><PolyStyle><color>#a00000ff</color><outline>0</outline></PolyStyle></Style>\n");
		builder.append("</Placemark>\n");
		
		builder.append(FOOTER);
		return builder.toString();
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
