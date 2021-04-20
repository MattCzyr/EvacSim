import shapely.geometry

class Polygon:
    """
    Represents a polygon represented by latitude/longitude points, for use in disaster data to define its area of effect at a given time
    """

    def __init__(self, points):
        """Create a new polygon from points, which should be an array of latitude/longitude tuples"""
        self.points = points

    def contains(self, lat, lng):
        """Returns true if the given latitude/longitude point is within this polygon, false otherwise"""
        shapely_polygon = shapely.geometry.Polygon(self.points)
        return shapely_polygon.contains(shapely.geometry.Point(lat, lng))

    def reverseLatLng(self):
        """Returns the points in this polygon in longitude/latitude form rather than latitude/longitude form"""
        return [(point[1], point[0]) for point in self.points]
