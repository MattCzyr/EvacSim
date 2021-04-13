class Route:

    def __init__(self, path, source, dest, travelers):
        self.path = path
        self.source = source
        self.dest = dest
        self.travelers = travelers

    def getTotalTravelTime(self):
        totalTravelTime = 0
        for edge in source.path:
            totalTravelTime += edge.travelTime
        return totalTravelTime

    def __str__(self):
        ret = f'Evac Route: travelers = {self.travelers}; path = {self.source.name}'
        prev_node = self.source
        for edge in self.path:
            other_node = edge.source
            if other_node == prev_node:
                other_node = edge.dest
            ret += f' -> {other_node.name}'
            prev_node = other_node
        return ret
