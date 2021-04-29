import argparse
import csv
import copy
from . import node
from . import edge
from . import disaster
from . import polygon
from . import route
from . import exporter

class EvacSim:
    """
    Represents the simulation itself, which is responsible for parsing arguments, loading models,
    running the flow algorithm, and exporting resulting data to a KML file
    """

    def __init__(self):
        self.args = {'nodes': 'nodes.csv', 'edges': 'edges.csv', 'disaster': 'disaster.csv', 'dir': 'models/troy_model/', 'export': 'export.kml','run':False}
        self.nodes = {}
        self.edges = []
        self.disaster = None
        self.routes = []

    def init_args(self):
        """Creates an argument parser, adds arguments, and returns the parser"""
        parser = argparse.ArgumentParser(description='EvacSim')
        parser.add_argument('--nodes', '-n', help='Specify nodes model file name')
        parser.add_argument('--edges', '-e', help='Specify edges model file name')
        parser.add_argument('--disaster', '-d', help='Specify disaster model file name')
        parser.add_argument('--dir', help='Specify directory to read models from')
        parser.add_argument('--export', help='Specify a filename for the exported KML data')
        parser.add_argument('--run', help='Choose whether or not to autorun exported KML file (provided Google Earth is installed)')
        return parser

    def parse_args(self, parser):
        """Parses arguments with the provided parser"""
        args = parser.parse_args()
        if args.nodes:
            self.args['nodes'] = args.nodes
        if args.edges:
            self.args['edges'] = args.edges
        if args.disaster:
            self.args['disaster'] = args.disaster
        if args.dir:
            self.args['dir'] = args.dir
        if args.export:
            self.args['export'] = args.export
        if args.run:
            self.args['run'] = args.run

    def load_models(self):
        """Loads the models from the file names in the arguments"""
        print('Loading nodes from ' + self.args['nodes'] + '...')
        with open(self.args['dir'] + self.args['nodes'] , mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                self.nodes[row['Name']] = node.Node(row['Name'], float(row['Latitude']), float(row['Longitude']), int(row['Population']), int(row['Capacity']))

        print('Loading edges from ' + self.args['edges'] + '...')
        with open(self.args['dir'] + self.args['edges'], mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                self.edges.append(edge.Edge(self.nodes[row['Source']], self.nodes[row['Destination']], int(row['Time']), 0, row['Capacity']))

        print('Loading disaster from ' + self.args['disaster'] + '...')
        with open(self.args['dir'] + self.args['disaster'], mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            disaster_created = False
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                # Creates a new disaster object if one has not yet been created
                if not disaster_created:
                    self.disaster = disaster.Disaster(row['Name'])
                    disaster_created = True
                index = 1
                points = []
                while f'Latitude{index}' in row and f'Longitude{index}' in row:
                    points.append((float(row[f'Latitude{index}']), float(row[f'Longitude{index}'])))
                    index += 1
                self.disaster.add_data(disaster.Disaster.Data(row['Time'], polygon.Polygon(points)))

    def get_affected_nodes(self):
        """Finds all nodes within the natural disaster's area of effect"""
        affected_nodes = []
        for node in self.nodes.values():
            for data in self.disaster.data:
                if data.effect.contains(node.lat, node.lng):
                    affected_nodes.append(node)
                    break
        return affected_nodes

    def generate_evacuation_routes(self):
        """Runs a minimum cost flow algorithm on each city within the natural disaster's area of effect to generate an evacuation route"""
        # All nodes that have been affected by the natural disaster
        affected_nodes = self.get_affected_nodes()
        # A relative model that will be altered as populations shift
        relative_nodes = copy.deepcopy(self.nodes)
        # Generate evacuation routes into this list
        evac_routes = []
        for affected_node in affected_nodes:
            self.generate_evacuation_routes_for_node(affected_node, affected_node, relative_nodes, [], [], evac_routes)
        self.routes = evac_routes

    def generate_evacuation_routes_for_node(self, node, affected_node, relative_nodes, visited_nodes, current_route, evac_routes):
        """Recursively generates evacuation routes for the affected node, ensuring that the population capacities
           of other nodes are not violated"""
        visited_nodes.append(node)
        for edge in self.get_connected_edges(node):
            # Make a copy of the evacuation route so far to avoid altering it directly
            new_route = copy.deepcopy(current_route)
            new_route.append(edge)
            # Find the node on the other side of this edge
            other_node = edge.source
            if edge.source == node:
                other_node = edge.dest
            # Skip if we've already been to this node
            if other_node in visited_nodes:
                continue
            if other_node not in self.get_affected_nodes() and relative_nodes[other_node.name].population < relative_nodes[other_node.name].capacity:
                # Sanity check to ensure we aren't wasting our time
                if relative_nodes[affected_node.name].population == 0:
                    return
                # Find the maximum population that can be transferred to this node
                transferable_population = relative_nodes[other_node.name].capacity - relative_nodes[other_node.name].population
                if relative_nodes[affected_node.name].population < transferable_population:
                    # Transfer the entire population to this node in the relative model
                    prev_population = relative_nodes[affected_node.name].population
                    relative_nodes[other_node.name].population += relative_nodes[affected_node.name].population
                    relative_nodes[affected_node.name].population = 0
                    # Create an evacuation route to this node
                    evac_routes.append(route.Route(new_route, affected_node, other_node, prev_population))
                else:
                    # Transfer the maximum population to this node in the relative model
                    relative_nodes[other_node.name].population += transferable_population
                    relative_nodes[affected_node.name].population -= transferable_population
                    # Create an evacuation route to this node
                    evac_routes.append(route.Route(new_route, affected_node, other_node, transferable_population))
            # Recurse on this node if necessary
            if relative_nodes[affected_node.name].population > 0 and other_node not in visited_nodes:
                self.generate_evacuation_routes_for_node(other_node, affected_node, relative_nodes, visited_nodes, new_route, evac_routes)

    def export_kml(self):
        """Exports the models to a KML file, allowing us to export geographic data to Google Earth and Google Maps, and display it accordingly"""
        print('Exporting models to ' + self.args['export'] + '...')
        exp = exporter.Exporter(self.nodes, self.edges, self.disaster, self.routes, self.args['export'])
        exp.export_kml()

    def get_connected_edges(self, node):
        """Returns all edges connected to the given node"""
        connected_edges = []
        for edge in self.edges:
            if node in (edge.source, edge.dest):
                connected_edges.append(edge)
        return connected_edges
