import unittest
import evacsim

class TestEvacSim(unittest.TestCase):

    def test_load_models(self):
        """Tests the get_affected_nodes function"""
        ev = evacsim.EvacSim()
        ev.load_models()
        self.assertGreater(len(ev.nodes), 0)
        self.assertGreater(len(ev.edges), 0)
        self.assertNotEqual(ev.disaster, None)

    def test_get_affected_nodes(self):
        """Tests the get_affected_nodes function"""
        ev = evacsim.EvacSim()
        ev.load_models()
        self.assertEqual(len(ev.get_affected_nodes()), 3)

        ev.disaster.data = []
        self.assertEqual(len(ev.get_affected_nodes()), 0)
    
    def test_get_connected_edges(self):
        """Tests the get_connected_edges function"""
        ev = evacsim.EvacSim()
        ev.load_models()
        self.assertEqual(len(ev.get_connected_edges(ev.nodes['Troy'])), 5)
        self.assertEqual(len(ev.get_connected_edges(ev.nodes['Albany'])), 3)
    
    def test_generate_evacuation_routes(self):
        """Tests the generate_evacuation_routes function"""
        ev = evacsim.EvacSim()
        ev.load_models()
        ev.generate_evacuation_routes()
        for node in ev.get_affected_nodes():
            self.assertEqual(node.population, 0)
            self.assertEqual(node.population, 0)
        for node in ev.nodes:
            self.assertLessEqual(node.population, node.capacity)
