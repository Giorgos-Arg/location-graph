# location-graph

This java program uses a graph to calculate the shortest distance between locations and to find the closest locations 
from any location.

## Description

By reading the the latitude, longitude and name of each location it builds a weighted graph where the weight is the 
distance between two locations. The distance is calculated using the 
[Haversine formula](https://en.wikipedia.org/wiki/Haversine_formula). Each location is directly connected with a maximum
of ten locations (this can be changed by changing the MAX_LIST_SIZE constant in the Graph class). The shortest distance
and the closest locations are calculated using 
[Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm).

## Usage

Compilation:

```bash
javac *.java
```

Run:

```bash
java LocationGraph <functionality> <input_file>:
```
Where:

| \<functionality> | Description                                        |
| ---------- | --------------------------------------------------- |
| -shortdist | print the shortest distance between two locations   |
| -nclosest  | print the n closest locations from a given location |


<input_file> is a CSV (Comma-Separated Values) file with three columns: Latitude, Longitude and Location Name
 
## Examples

```bash
java LocationGraph -shortdist ../data/cy-locations.csv

Creating graph...

Graph is ready! You can view the graph in the file /data/graph.csv.

Give the first location:
esso galata

Give the second location:
kalyana

The shortest between esso galata and kalyana distance is : 2.12 km
```

```bash
java LocationGraph -nclosest ../data/cy-locations.csv

Creating graph...

Graph is ready! You can view the graph in the file /data/graph.csv.

Give a location:
sina oros

Give n:
10

The 10 closest locations from sina oros are:
1: panagia theotokos
2: esso galata
3: ayia anna
4: kalyana
5: argaki tou karvouna
6: vounaras
7: staoudies
8: virviros
9: argaki tou petkou
10: moutti tou zoumi
```
## Author

Giorgos Argyrides (g.aryrides@outlook.com)
