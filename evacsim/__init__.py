from evacsim import EvacSim
import os
# Instantiates a new EvacSim object, and parses the given cmdline arguments (or uses default if left blank).
# Models are then loaded from provided arguments or defaults, and exported accordingly to .KML files.
ev = EvacSim()
ev.parse_args(ev.init_args())
ev.load_models()
ev.generate_evacuation_routes()
ev.export_kml()
# If 'run' arg is true, open exported KML file in Google Earth Pro
if(ev.args['run']):
    os.startfile('export.kml')

# Example usage from the root directory:
# python evacsim/__init__.py --nodes nodes.csv --edges edges.csv --disaster disaster.csv --dir models/troy_model/ --export export.kml
# These arguments can be omitted to use the default values.
