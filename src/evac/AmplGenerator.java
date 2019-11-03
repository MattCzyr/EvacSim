package evac;

import java.util.List;

public class AmplGenerator {
	public static String createData(InputModel model, List<Node> evac, List<Node> dest) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("set EVAC := ");
		for (Node n : evac) {
			builder.append(n.name);
			builder.append(" ");
		}
		builder.append(";\n");
		
		builder.append("set DEST := ");
		for (Node n : dest) {
			builder.append(n.name);
			builder.append(" ");
		}
		builder.append(";\n");
		
		builder.append("param: EDGES : edgecapacity :=\n");
		for (Edge edge : model.edges) {
			builder.append(String.format("  %s %s %f\n", edge.src.name, edge.dst.name, edge.capacity));
		}
		builder.append(";\n");
		
		builder.append("param population :=\n");
		for (Node n : evac) {
			builder.append(String.format("  %s %d\n", n.name, n.population));
		}
		builder.append(";\n");
		
		builder.append("param capacity :=\n");
		for (Node n : dest) {
			builder.append(String.format("  %s %d\n", n.name, n.capacity - n.population));
		}
		builder.append(";\n");
		
		
		return builder.toString();
	}
}
