import simplekml

class Exporter:

    def __init__(self, nodes, edges, disaster, routes, filename):
        self.nodes = nodes
        self.edges = edges
        self.disaster = disaster
        self.routes = routes
        self.filename = filename
    
    def export_kml(self):
        """Exports the nodes (cities), edges (infrastructure), disaster data, and evacuation routes to a KML file"""

        # Create a new KML file representation. Keep in mind that all coordinates in this representation must be in longitude/latitude form, rather than latitude/longitude as in other parts of the project.
        kml = simplekml.Kml()

        # Add nodes to KML
        for node in self.nodes.values():
            kml.newpoint(name=node.name, coords=[(node.lng, node.lat)], description='Test node')
        
        # Add edges to KML
        for edge in self.edges:
            kml.newlinestring(name=f'Edge between {edge.source.name}, {edge.dest.name}', coords=[(edge.source.lng, edge.source.lat), (edge.dest.lng, edge.dest.lat)])
        
        # Add disaster data to KML
        for datum in self.disaster.data:
            kml.newpolygon(name=self.disaster.name, description=str(datum.time), outerboundaryis=[(datum.effect.lng1, datum.effect.lat1), (datum.effect.lng2, datum.effect.lat2), (datum.effect.lng3, datum.effect.lat3), (datum.effect.lng4, datum.effect.lat4)])
        
        # Export KML file
        kml.save(self.filename)
