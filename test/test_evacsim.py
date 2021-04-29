import unittest
import evacsim.evacsim

class TestEvacSim(unittest.TestCase):
    """Tests functionality in the evacsim module. These tests are meant to be generic, meaning they will still pass if
       the Troy model is changed. For example, test_load_models simply tests that nodes, edges, and a natural disaster
       exist after load_models is called, rather than ensuring that any specific objects are created."""

    def test_load_models(self):
        """Tests the get_affected_nodes function"""
        ev = evacsim.evacsim.EvacSim()
        ev.load_models()
        self.assertGreater(len(ev.nodes), 0)
        self.assertGreater(len(ev.edges), 0)
        self.assertNotEqual(ev.disaster, None)

    def test_get_affected_nodes(self):
        """Tests the get_affected_nodes function"""
        ev = evacsim.evacsim.EvacSim()
        ev.load_models()
        for node in ev.nodes.values():
            for data in ev.disaster.data:
                if data.effect.contains(node.lat, node.lng):
                    self.assertTrue(node in ev.get_affected_nodes())
    
    def test_get_connected_edges(self):
        """Tests the get_connected_edges function"""
        ev = evacsim.evacsim.EvacSim()
        ev.load_models()
        for edge in ev.edges:
            self.assertTrue(edge in ev.get_connected_edges(edge.dest))
            self.assertTrue(edge in ev.get_connected_edges(edge.source))
    
    def test_generate_evacuation_routes(self):
        """Tests the generate_evacuation_routes function"""
        ev = evacsim.evacsim.EvacSim()
        ev.load_models()
        ev.generate_evacuation_routes()
        for node in ev.get_affected_nodes():
            evacuated_population = 0
            for route in ev.routes:
                if route.source == node:
                    evacuated_population += route.travelers
            self.assertEqual(evacuated_population, node.population)
        for node in ev.nodes.values():
            new_population = node.population
            for route in ev.routes:
                if route.dest == node:
                    new_population += route.travelers
            self.assertLessEqual(new_population, node.capacity)
