class Route:
    """
    Represents an evacuation route in the simulation, represented by a list of edges from a source node to a destination node
    """

    def __init__(self, path, source, dest, travelers):
        self.path = path
        self.source = source
        self.dest = dest
        self.travelers = travelers

    def get_node_path(self):
        """Returns the nodes visited in order in this route"""
        node_path = []
        node_path.append(self.source)
        for edge in self.path:
            other_node = edge.source
            if other_node == node_path[-1]:
                other_node = edge.dest
            node_path.append(other_node)
        return node_path

    def get_total_travel_time(self):
        """Returns the total time needed to travel all the edges in this route"""
        total_travel_time = 0
        for edge in self.path:
            total_travel_time += edge.travel_time
        return total_travel_time

    def __str__(self):
        ret = f'Evac Route: travelers = {self.travelers}; path = {self.source.name}'
        for node in self.get_node_path()[1:]:
            ret += f' -> {node.name}'
        return ret
