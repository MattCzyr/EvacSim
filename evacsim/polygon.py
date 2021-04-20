import shapely.geometry

class Polygon:
    """
    Represents a polygon represented by latitude/longitude points, for use in disaster data to define its area of effect at a given time
    """

    def __init__(self, lat1, lng1, lat2, lng2, lat3, lng3, lat4, lng4):
        self.lat1 = lat1
        self.lng1 = lng1
        self.lat2 = lat2
        self.lng2 = lng2
        self.lat3 = lat3
        self.lng3 = lng3
        self.lat4 = lat4
        self.lng4 = lng4

    def contains(self, lat, lng):
        """Returns true if the given latitude/longitude point is within this polygon, false otherwise"""
        shapely_polygon = shapely.geometry.Polygon([(self.lat1, self.lng1), (self.lat2, self.lng2), (self.lat3, self.lng3), (self.lat4, self.lng4)])
        return shapely_polygon.contains(shapely.geometry.Point(lat, lng))
