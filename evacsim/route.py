class Route:

    def __init__(self, source, path, dest):
        self.path = path
    
    def getSource(self):
        return self.path[0].source
    
    def getDest(self):
        return self.path[-1].dest

    def getTotalTravelTime(self):
        totalTravelTime = 0
        for edge in source.path:
            totalTravelTime += edge.travelTime
        return totalTravelTime
