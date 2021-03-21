import argparse
import parser
import csv

class EvacSim:

    def __init__(self):
        self.args = {}
        # self.parse_args(self.init_args())
    
    def init_args(self):
        """Creates an argument parser, adds arguments, and returns the parser"""
        parser = argparse.ArgumentParser(description='EvacSim')
        parser.add_argument('--nodes', '-n', help='Specify nodes model file name')
        parser.add_argument('--edges', '-e', help='Specify edges model file name')
        parser.add_argument('--disaster', '-d', help='Specify disaster model file name')
        parser.add_argument('--main', '-m', help='Specify main model file name')
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
    
    def load_models(self):
        """Loads the models from the file names in the arguments"""
        print("loading nodes from " + self.args['nodes'])
        with open("models/"+self.args['nodes'], mode='r') as csv_file:
            csv_nodes = csv.DictReader(csv_file)


        print("loading edges from " + self.args['edges'])
        with open("models/"+self.args['edges'], mode='r') as csv_file:
            csv_edges = csv.DictReader(csv_file)
 

        print("loading disaster from " + self.args['disaster'])
        with open("models/"+self.args['disaster'], mode='r') as csv_file:
            csv_disaster = csv.DictReader(csv_file)

        print("loading main from " + self.args['main'])
        with open("models/"+self.args['main'], mode='r') as csv_file:
            csv_main = csv.DictReader(csv_file)
