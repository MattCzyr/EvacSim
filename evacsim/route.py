class Route:

    def __init__(self, path, source, dest, travelers):
        self.path = path
        self.source = source
        self.dest = dest
        self.travelers = travelers
    
    def getNodePath(self):
        node_path = []
        node_path.append(self.source)
        for edge in self.path:
            other_node = edge.source
            if other_node == node_path[-1]:
                other_node = edge.dest
            node_path.append(other_node)
        return node_path

    def getTotalTravelTime(self):
        totalTravelTime = 0
        for edge in self.path:
            totalTravelTime += edge.travelTime
        return totalTravelTime

    def __str__(self):
        ret = f'Evac Route: travelers = {self.travelers}; path = {self.source.name}'
        for node in self.getNodePath()[1:]:
            ret += f' -> {node.name}'
        return ret
