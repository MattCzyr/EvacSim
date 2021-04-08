import argparse
import parser
import csv
import os
import node
import edge
import disaster
import polygon
import exporter

class EvacSim:

    def __init__(self):
        self.args = {'nodes': 'nodes.csv', 'edges': 'edges.csv', 'disaster': 'disaster.csv', 'dir': 'models/troy_model/', 'export': 'export.kml'}
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
    
    def load_models(self):
        """Loads the models from the file names in the arguments"""
        print('Loading nodes from ' + self.args['nodes'] + '...')
        with open(self.args['dir'] + self.args['nodes'] , mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                self.nodes[row['Name']] = node.Node(row['Name'], float(row['Latitude']), float(row['Longitude']), row['Population'], row['Capacity'])

        print('Loading edges from ' + self.args['edges'] + '...')
        with open(self.args['dir'] + self.args['edges'], mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                self.edges.append(edge.Edge(self.nodes[row['Source']], self.nodes[row['Destination']], row['Time'], 0, row['Capacity']))

        print('Loading disaster from ' + self.args['disaster'] + '...')
        with open(self.args['dir'] + self.args['disaster'], mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            disaster_created = False
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                if not disaster_created:
                    self.disaster = disaster.Disaster(row['Name'])
                    disaster_created = True
                self.disaster.add_data(disaster.Disaster.Data(row['Time'], polygon.Polygon(float(row['Latitude1']), float(row['Longitude1']), float(row['Latitude2']), float(row['Longitude2']), float(row['Latitude3']), float(row['Longitude3']), float(row['Latitude4']), float(row['Longitude4']))))
    
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
        for affected_node in self.get_affected_nodes():
            for connected_node in self.get_connected_nodes(affected_node):
                pass

    def export_kml(self):
        """Exports the models to a KML file"""
        print('Exporting models to ' + self.args['export'] + '...')
        exp = exporter.Exporter(self.nodes, self.edges, self.disaster, self.routes, self.args['export'])
        exp.export_kml()
    
    def get_connected_nodes(self, node):
        """Returns all nodes connected to the given node"""
        connected_nodes = []
        for edge in self.edges:
            if edge.source == node:
                connected_edges.append(edge.dest)
            elif edge.dest == node:
                connected_edges.append(edge.source)
        return connected_edges
