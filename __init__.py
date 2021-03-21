

from evacsim import EvacSim

ev = EvacSim()
print(ev.args)
ev.load_models()

#python __init__.py --nodes nodes.csv --edges edges.csv --disaster hurricanes.csv --main main.csv
