

from evacsim import EvacSim

ev = EvacSim()
ev.parse_args(ev.init_args())
ev.load_models()

#python __init__.py --nodes nodes.csv --edges edges.csv --disaster hurricanes.csv --main main.csv
