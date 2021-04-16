from evacsim import EvacSim

ev = EvacSim()
ev.parse_args(ev.init_args())
ev.load_models()
ev.generate_evacuation_routes()
ev.export_kml()

# Example usage from the root directory:
# python evacsim/__init__.py --nodes nodes.csv --edges edges.csv --disaster disaster.csv --dir troy_model --export export.kml
# These arguments can be omitted to use the default values.
