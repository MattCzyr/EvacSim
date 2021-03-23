import argparse
import parser
import csv
import os
import node
import edge
import disaster
import polygon

class EvacSim:

    def __init__(self):
        self.args = {'nodes': 'nodes.csv', 'edges': 'edges.csv', 'disaster': 'disaster.csv', 'main': 'main.csv', 'dir': 'models/troy_model/'}
        self.nodes = {}
        self.edges = []
        self.disaster = None
    
    def init_args(self):
        """Creates an argument parser, adds arguments, and returns the parser"""
        parser = argparse.ArgumentParser(description='EvacSim')
        parser.add_argument('--nodes', '-n', help='Specify nodes model file name')
        parser.add_argument('--edges', '-e', help='Specify edges model file name')
        parser.add_argument('--disaster', '-d', help='Specify disaster model file name')
        parser.add_argument('--main', '-m', help='Specify main model file name')
        parser.add_argument('--dir', help='Specify directory to read models from')
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
        if args.main:
            self.args['main'] = args.main
        if args.dir:
            self.args['dir'] = args.dir
    
    def load_models(self):
        """Loads the models from the file names in the arguments"""
        print('Loading nodes from ' + self.args['nodes'] + '...')
        with open(self.args['dir'] + self.args['nodes'] , mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
                self.nodes[row['Name']] = node.Node(row['Name'], row['Latitude'], row['Longitude'], row['Population'], row['Capacity'])

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
                self.disaster.add_data(disaster.Disaster.Data(row['Time'], polygon.Polygon(row['Latitude1'], row['Longitude1'], row['Latitude2'], row['Longitude2'], row['Latitude3'], row['Longitude3'], row['Latitude4'], row['Longitude4'])))
        
        print('Loading main from ' + self.args['main'] + '...')
        with open(self.args['dir'] + self.args['main'], mode='r') as csv_file:
            data = csv.DictReader(csv_file)
            for row in data:
                if int(row['Enabled']) != 0:
                    continue
