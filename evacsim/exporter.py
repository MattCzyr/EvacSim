import simplekml

class Exporter:
    """
    Responsible for exporting nodes, edges, disaster data, and evacuation routes to a KML file
    """

    def __init__(self, nodes, edges, disaster, routes, filename):
        self.nodes = nodes
        self.edges = edges
        self.disaster = disaster
        self.routes = routes
        self.filename = filename
        self.route_colors = [simplekml.Color.blue, simplekml.Color.red, simplekml.Color.green, simplekml.Color.purple, simplekml.Color.orange]

    def export_kml(self):
        """Exports the nodes (cities), edges (infrastructure), disaster data, and evacuation routes to a KML file"""

        # Create a new KML file representation. Keep in mind that all coordinates in this representation must be in longitude/latitude form, rather than latitude/longitude as in other parts of the project.
        kml = simplekml.Kml()

        # Add nodes to KML
        for node in self.nodes.values():
            desc = f'Population: {node.population}\nCapacity: {node.capacity}'
            for route in self.routes:
                if route.source == node:
                    desc += f'\n{route}'
            kml.newpoint(name=node.name, description=desc, coords=[(node.lng, node.lat)])

        # Add edges to KML
        for edge in self.edges:
            edge_line = kml.newlinestring(name=f'Infrastructure between {edge.source.name} and {edge.dest.name}', description=f'Travel Time: {edge.travel_time}\nCapacity: {edge.capacity}', coords=[(edge.source.lng, edge.source.lat), (edge.dest.lng, edge.dest.lat)])
            edge_line.style.linestyle.color = simplekml.Color.lightgray

        # Add disaster data to KML
        for datum in self.disaster.data:
            kml.newpolygon(name=self.disaster.name, description=f'Time: {str(datum.time)}', outerboundaryis=datum.effect.reverse_lat_lng())

        # Assign colors to each affected city's evacuation routes
        source_colors = {}
        color_index = 0
        for route in self.routes:
            if route.source.name not in source_colors:
                source_colors[route.source.name] = self.route_colors[color_index]
                # Increment color index for next route
                color_index += 1
                if color_index >= len(self.route_colors):
                    color_index = 0
        
        # Assign widths to each affected city's evacuation routes
        source_widths = {}
        width_index = 0
        for node_name in source_colors:
            source_widths[node_name] = (len(source_colors) - width_index) * 8
            width_index += 1

        # Add evacuation routes to KML
        for route in self.routes:
            # Get the coordinates for each node in the path
            path_coords = [(node.lng, node.lat) for node in route.get_node_path()]

            # Create and style linestring for this route
            route_line = kml.newlinestring(name=f'Evacuation route from {route.source.name} to {route.dest.name}', description=f'Total Travel Time: {route.get_total_travel_time()}', coords=path_coords)
            route_line.style.linestyle.color = source_colors[route.source.name]
            route_line.style.linestyle.width = 4

        # Export KML file
        kml.save(self.filename)
