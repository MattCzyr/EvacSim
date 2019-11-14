# EvacSim

EvacSim is a deterministic simulation that uses graph theory and linear
programming to model the effects of hurricanes on communities in order to
predict optimal evacuation routes. This project was originally developed for
HackRPI 2019, a 24-hour hackathon in which it won 2nd place.

## Background

This project was originally developed for HackRPI 2019, a 24-hour hackathon in
which it won 2nd place. The idea was born after witnessing the devastating
effects of Hurricane Dorian as well as hearing from friends and family in
tropical regions who were forced to evacuate their homes due to storms numerous
times over the years.

## How it works

### Model

In the model, populations are represented by nodes and the infrastructure
between them are represented by edges. These nodes and edges can then be used
to construct a graph. The actual modeling happens in CSV files, such as the
following `nodes.csv` file modeling populations around Troy, NY:

| Enabled | Name          | Latitude | Longitude | Population | Capacity |
| ------- | ------------- | -------- | --------- | ---------- | -------- |
| 0       | Troy          | 42.718   | -73.687   | 50000      | 60000    |
| 0       | Albany        | 42.649   | -73.753   | 98000      | 120000   |
| 0       | Guilderland   | 42.702   | -73.909   | 36000      | 50000    |
| 0       | EastGreenbush | 42.588   | -73.703   | 16000      | 30000    |
| 0       | Brunswick     | 42.732   | -73.562   | 16000      | 35000    |
| 0       | Schenectady   | 42.806   | -73.943   | 65000      | 70000    |

Similar models exist for edges, hurricanes, and the main setup:
- `edges.csv`: Models infrastructure (ie. roads) between populations by source
  node, destination node, transit time, and maximum capacity.
- `hurricanes.csv`: Models the hurricane by initial position, wind speed
  variation, velocity, and trajectory.
- `main.csv`: Models the start and end time for the simulation.

Examples can be found in the [models](../models/) folder.

### Simulation

The simulation has three main parts: the simulator itself, which is written in
Java, the AMPL model,  and the boilerplate script, which is written in
JavaScript with Node.js. The script first fetches the model files from where
they are stored in the IBM cloud. The files are then passed to the simulator,
which translates the model files to usable data. From the hurricane data, the
path and area of effect of the storm is determined, which is then used to
determine which populations should evacuate. The data for edges, nodes, and
affected nodes are then passed to an AMPL model which runs a minimum flow
algorithm on the graph to determine where each affected population can evacuate
to without violating maximum capacities on communities and infrastructure. This
data is passed back to the Java simulation, which generates a KML file showing
the area of effect of the hurricane, the severity in each region, the evacuation
routes for each affected community, and data such as the final populations in
each community. This KML file can be displayed in a geographic browser such as
Google Earth.

## What's next

Some possibilities for extensions of the project include:
- Supporting multiple types of natural disasters, such as earthquakes and
  wildfires
- Improving the accuracy of hurricane representations by allowing for varying
  velocities and trajectories
- Using IBM Cloud services more extensively
- Creating a custom frontend to display evacuation routes rather than
  generating KML files

