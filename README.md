# EvacSim

EvacSim is a simulation that uses graph theory and linear programming to model
the effects of hurricanes on populations in order to predict optimal evacuation
routes.

## How does it work?

We use a graph to represent populations and the infrastructure between them:
nodes represent cities, each with a current population and a population
capacity, and edges represent roads between them, each with a transit time and
traffic capacity. We then determine the areas that will be affected by the
hurricane, based on the velocity and wind speed variation (and subsequentially
the hurricane category). Once we have calculated these "danger zones", we run a
minimum-cost-flow algorithm on the graph to determine the optimal evacuation
routes for each affected population. Sometimes, a single population will need
to be split into multiple evacuation routes to avoid violating maximum
population capacities.
