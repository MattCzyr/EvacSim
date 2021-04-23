import unittest
import node

class TestNode(unittest.TestCase):

    def test_eq(self):
        """Tests the __eq__ function"""
        node1 = node.Node('Troy', 42.727453, -73.691764, 50000, 80000)
        node2 = node.Node('Troy', 42.726311, -73.670170, 48000, 75000)
        node3 = node.Node('Watervliet', 42.730389, -73.701504, 10000, 15000)
        self.assertEqual(node1, node2)
        self.assertNotEqual(node1, node3)
        self.assertNotEqual(node2, node3)
