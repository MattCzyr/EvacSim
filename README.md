# EvacSim

EvacSim is a deterministic simulation that uses graph theory and linear
programming to model the effects of natural disasters on communities in order
to find optimal evacuation routes. This project was originally developed for
HackRPI 2019, a 24-hour hackathon in which it won 2nd place.

## Background

This project was originally developed for HackRPI 2019, a 24-hour hackathon in
which it won 2nd place. It used Java for model translation and representation,
AMPL to run the flow algorithm, and Node to hold the whole thing together.
This has since been replaced entirely by Python.

The idea for the project was born after witnessing the devastating effects of
Hurricane Dorian as well as hearing from friends and family in tropical regions
who were forced to evacuate their homes due to storms numerous times over the
years. As such, the application was initially only able to simulate hurricanes.
This has since been replaced by a generic natural disaster representation,
modeled by polygons corresponding to the disaster's area of effect at different
times.

## How it works

### Model

In the model, populations are represented by nodes and the infrastructure
between them are represented by edges. These nodes and edges can then be used
to construct a graph. The actual modeling happens in CSV files, such as the
following `nodes.csv` file modeling populations around Troy, NY:

| Enabled | Name           | Latitude | Longitude | Population | Capacity |
| ------- | -------------- | -------- | --------- | ---------- | -------- |
| 0       | Troy           | 42.718   | -73.687   | 50000      | 60000    |
| 0       | Albany         | 42.649   | -73.753   | 100000     | 125000   |
| 0       | Guilderland    | 42.702   | -73.909   | 35000      | 50000    |
| 0       | East Greenbush | 42.588   | -73.703   | 15000      | 20000    |
| 0       | Brunswick      | 42.732   | -73.562   | 15000      | 30000    |
| 0       | Schenectady    | 42.806   | -73.943   | 65000      | 80000    |
| 0       | Latham         | 42.748   | -73.761   | 20000      | 30000    |

Similar models exist for edges and natural disasters:
- `edges.csv`: Models infrastructure (ie. roads) between populations by source
  node, destination node, transit time, and maximum capacity.
- `disaster.csv`: Models the natural disaster with polygons, representing its
  area of effect at different times. Area of effect polygons can have any
  number of sides, as long as they are ordered clockwise or counter-clockwise.

Examples can be found in the [models](../models/) folder.

### Simulation

Once the models have been read, an algorithm is run on each city within the
natural disaster's area of effect. This algorithm is similar to a minimum-cost
flow algorithm: it aims to find an evacuation route each at-risk community can
take to reach a safe city in the least amount of time, without violating
constraints like maximum capacities on cities and infrastructure.

### Results

After running the simulation, the results can be exported to a KML file. This
file can be viewed in an Earth browser such as Google Earth, where the cities,
infrastructure, natural disaster, and evacuation routes can be explored, as
well as their supporting data.

## Setting up

1. Install Python 3 through the [installer](https://www.python.org/downloads/)
   or through a package manager like Brew or APT depending on your OS
2. Install the virtualenv package with `pip3 install virtualenv`
3. Create a new virtual environment with `python3 -m virtualenv .venv`
4. Activate the virtual environment with `source .venv/bin/activate` on
   macOS/Linux or `.venv\Scripts\activate` on Windows
5. Install the project's dependencies with `pip3 install -r requirements.txt`
6. You can now run the application with `python3 evacsim/__init__.py`,
   specifying arguments as necessary
7. When you're finished, deactivate the virtual environment with `deactivate`

