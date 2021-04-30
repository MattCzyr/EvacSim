import unittest
import os
import evacsim.node
import evacsim.edge
import evacsim.disaster
import evacsim.exporter

class TestExporter(unittest.TestCase):
    """Tests functionality in the exporter module. There isn't much to be tested here, so it simply tests
       that a KML file with the proper name is created when the export_kml function is called."""

    def test_export_kml(self):
        """Tests the export_kml function"""
        nodes = {'Troy': evacsim.node.Node('Troy', 42.727453, -73.691764, 50000, 80000), 'Watervliet': evacsim.node.Node('Watervliet', 42.730389, -73.701504, 10000, 15000)}
        edges = [evacsim.edge.Edge(nodes['Troy'], nodes['Watervliet'], 25, 0, 1000)]
        disaster = evacsim.disaster.Disaster('Alfred')
        routes = []
        exp = evacsim.exporter.Exporter(nodes, edges, disaster, routes, 'test.kml')
        exp.export_kml()
        self.assertTrue(os.path.exists('test.kml'))
        os.remove('test.kml')
