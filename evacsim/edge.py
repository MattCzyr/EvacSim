class Edge:

    def __init__(self, source, dest, travelTime, travelers, capacity):
        self.source = source
        self.dest = dest
        self.travelTime = travelTime
        self.travelers = travelers
        self.capacity = capacity
    
    def __str__(self):
        return f'Edge; source = {self.source.name}; dest = {self.dest.name}; travelTime = {self.travelTime}; travelers = {self.travelers}; capacity = {self.capacity}'
