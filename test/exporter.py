import unittest
import node
import edge
import disaster
import exporter
import os

class TestExporter(unittest.TestCase):

    def test_export_kml(self):
        """Tests the export_kml function"""
        nodes = {'Troy': node.Node('Troy', 42.727453, -73.691764, 50000, 80000), 'Watervliet': node.Node('Watervliet', 42.730389, -73.701504, 10000, 15000)}
        edges = [edge.Edge(nodes['Troy'], nodes['Watervliet'], 25, 0, 1000)]
        disaster = disaster.Disaster('Alfred')
        exp = exporter.Exporter(nodes, edges, disaster, 'test.kml')
        exp.export_kml()
        self.assertTrue(os.path.exists('test.kml'))
        os.remove('test.kml')
