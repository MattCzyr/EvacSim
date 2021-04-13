class Node:

    def __init__(self, name, lat, lng, population, capacity):
        self.name = name
        self.lat = lat
        self.lng = lng
        self.population = population
        self.capacity = capacity

    def __str__(self):
        return f'Node; name = {self.name}; lat = {self.lat}; lng = {self.lng}; population = {self.population}; capacity = {self.capacity}'
