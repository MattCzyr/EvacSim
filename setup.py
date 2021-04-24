import kml2geojson
import json 
import pandas as pd
import geojsonio
kml2geojson.main.convert('./export.kml', './')

geodata = pd.read_json('export.geojson')
geojsonio.display(geodata)