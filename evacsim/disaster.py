class Disaster:

    def __init__(self, name):
        self.name = name
        self.data = []
    
    def add_data(self, data):
        self.data.append(data)

    class Data:
        
        def __init__(self, time, effect):
            self.time = time
            self.effect = effect
