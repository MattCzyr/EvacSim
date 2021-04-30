class Edge:
    """
    Represents an edge between two nodes in the simulation
    """

    def __init__(self, source, dest, travel_time, travelers, capacity):
        self.source = source
        self.dest = dest
        self.travel_time = travel_time
        self.travelers = travelers
        self.capacity = capacity

    def __str__(self):
        return f'Edge; source = {self.source.name}; dest = {self.dest.name}; travel_time = {self.travel_time}; travelers = {self.travelers}; capacity = {self.capacity}'
