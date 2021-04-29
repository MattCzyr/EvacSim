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
        for node in ev.nodes:
            for data in ev.disaster.data:
                if data.effect.contains(node.lat, node.lng):
                    self.assertTrue(node in ev.get_affected_nodes())
    
    def test_get_connected_edges(self):
        """Tests the get_connected_edges function"""
        ev = evacsim.EvacSim()
        ev.load_models()
        for edge in ev.edges:
            self.assertTrue(edge.source in ev.get_connected_edges(edge.dest))
            self.assertTrue(edge.dest in ev.get_connected_edges(edge.source))
    
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
