import unittest
import node
import edge
import route

class TestRoute(unittest.TestCase):

    def test_get_node_path(self):
        """Tests the get_node_path function"""
        node1 = node.Node('Troy', 42.718, -73.687, 50000, 60000)
        node2 = node.Node('Albany', 42.649, -73.753, 100000, 140000)
        node3 = node.Node('Latham', 42.748, -73.761, 20000, 30000)

        edge1 = edge.Edge(node1, node2, 20, 0, 2000)
        edge2 = edge.Edge(node2, node3, 25, 0, 1000)

        route1 = route.Route([edge1], node1, node2, 2000)
        self.assertEqual(route1.get_node_path(), [node1, node2])

        route2 = route.Route([edge1, edge2], node1, node3, 1000)
        self.assertEqual(route2.get_node_path(), [node1, node2, node3])

    def test_get_total_travel_time(self):
        """Tests the get_total_travel_time function"""
        node1 = node.Node('Troy', 42.718, -73.687, 50000, 60000)
        node2 = node.Node('Albany', 42.649, -73.753, 100000, 140000)
        node3 = node.Node('Latham', 42.748, -73.761, 20000, 30000)

        edge1 = edge.Edge(node1, node2, 20, 0, 2000)
        edge2 = edge.Edge(node2, node3, 25, 0, 1000)

        route1 = route.Route([edge1], node1, node2, 1000)
        self.assertEqual(route1.get_total_travel_time(), 20)

        route2 = route.Route([edge1, edge2], node1, node3, 1000)
        self.assertEqual(route2.get_total_travel_time(), 45)
