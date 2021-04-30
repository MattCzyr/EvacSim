class Disaster:
    """
    Represents a disaster in the simulation, modeled by polygons representing the area of effect
    at different times
    """

    def __init__(self, name):
        self.name = name
        self.data = []

    def add_data(self, data):
        """Adds new disaster data to this disaster"""
        self.data.append(data)

    def __str__(self):
        ret = f'Disaster; name = {self.name}; data = [ '
        for datum in self.data:
            ret += datum + '; '
        ret += ']'
        return ret

    class Data:
        """Represents disaster data, defining the area of effect of the disaster at a given time"""

        def __init__(self, time, effect):
            self.time = time
            self.effect = effect

        def __str__(self):
            return f'Data; time = {self.time}; effect = {self.effect}'
