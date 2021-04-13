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
