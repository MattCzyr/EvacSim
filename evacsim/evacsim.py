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

    # Declares default arguments and default directories, to allow for easier execution.
    def __init__(self):
        self.args = {'nodes': 'nodes.csv', 'edges': 'edges.csv', 'disaster': 'disaster.csv', 'dir': 'models/troy_model/', 'export': 'export.kml','run':False}
        self.nodes = {}
        self.edges = []
        self.disaster = None
        self.routes = []
    
    # Adds arguments accordingly if provided in the command line 
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
                # pushes each row back into the Node class Object collection
                self.nodes[row['Name']] = node.Node(row['Name'], row['Latitude'], row['Longitude'], row['Population'], row['Capacity'])

        print('Loading edges from ' + self.args['edges'] + '...')
        with open(self.args['dir'] + self.args['edges'], mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                # pushes each row back into the Edges class Object collection
                self.edges.append(edge.Edge(self.nodes[row['Source']], self.nodes[row['Destination']], row['Time'], 0, row['Capacity']))

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
                # pushes each row back into the Disaster class Object collection
                self.disaster.add_data(disaster.Disaster.Data(row['Time'], polygon.Polygon(row['Latitude1'], row['Longitude1'], row['Latitude2'], row['Longitude2'], row['Latitude3'], row['Longitude3'], row['Latitude4'], row['Longitude4'])))
    
    def export_kml(self):
        """Exports the models to a KML file, allowing us to export geographic data to Google Earth and Google Maps, and display it accordingly"""
        print('Exporting models to ' + self.args['export'] + '...')
        exp = exporter.Exporter(self.nodes, self.edges, self.disaster, self.routes, self.args['export'])
        exp.export_kml()
