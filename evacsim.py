import argparse
import parser

class EvacSim(object):

    def __init__(self):
        self.args = []
        self.parse_args(self.init_args())
    
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
        # TODO
