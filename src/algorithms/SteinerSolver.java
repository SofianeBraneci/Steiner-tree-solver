package algorithms;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SteinerSolver {
	private Kruskal kruskal;
	private FloydWarshallPathFinder pathFinder;
	private List<Point> points, hitPoints;

	public SteinerSolver(List<Point> points, List<Point> hitPoints) {
		this.points = points;
		this.hitPoints = hitPoints;
		pathFinder = new FloydWarshallPathFinder(points.size());

	}

	public Tree2D solve(double threshold) {
		System.out.println("************************ no restriction ***********************");

		// init the distance matrix
		pathFinder.computeDistances(points, threshold);
		// compute all pair shortest paths using FLOYD WARSHALL
		pathFinder.allShortesPath();
		// build a complete G graph such that the nodes are the hit points
		// and the edges have a weight representing the shortest path cost
		// obtained by calculating the APSP

		// using sets to prevent duplicate edges
		Set<Edge> completeHitPointsGraphEdges = new HashSet<>();
		for (Point source : hitPoints) {
			for (Point dist : hitPoints) {
				if (source == dist)
					continue;
				completeHitPointsGraphEdges
						.add(new Edge(source, dist, pathFinder.getDist(points.indexOf(source), points.indexOf(dist))));
			}
		}

		// compute the Minimum Spanning Tree of G
		kruskal = new Kruskal(completeHitPointsGraphEdges, hitPoints);

		// Let treeEdges(even if here it's a list but it is computed as a set)
		// be the set of edges representing the tree edges
		// calculated by Kruskal
		List<Edge> treeEdges = kruskal.computeTree();

		// here we can clearly see that the returned edges form a tree
		// because the size of the set is n - 1, where n is the number of hit points
		System.out.println("size tree edges = " + treeEdges.size());

		// now that we successfully compute our MST of the hit points
		// the next step is the replace every edge in the set of treeEdges
		// the full path joining both ends of the edge

		Set<Edge> replacedEdges = new HashSet<>();
		for (Edge edge : treeEdges) {
			List<Integer> path = pathFinder.reconstructShortesPath(points.indexOf(edge.getFrom().getRoot()),
					points.indexOf(edge.getTo().getRoot()));
			for (int i = 0; i < path.size() - 1; i++) {
				replacedEdges.add(new Edge(points.get(path.get(i)), points.get(path.get(i + 1)),
						pathFinder.getDist(path.get(i), path.get(i + 1))));
			}
		}
		// clearly after replacing the edges by the corresponding shortest path
		// the size of the set grows, and we may have created cycles in the new Tree
		// the solution to overcome these cycles is to recompute the MST using Kruskal
		// on the new Tree
		System.out.println("replaced edges size = " + replacedEdges.size());
		kruskal = new Kruskal(replacedEdges, points);
		treeEdges = kruskal.computeTree();
		//
		System.out.println("Final tree size = " + treeEdges.size());

		// reconstruct the tree from the final computed tree edges, and return the root
		return new Graph(treeEdges, hitPoints).transform();

	}

	public Tree2D solveBudget(double threshold) {
		System.out.println("************************ with budget ***********************");
		// init the distance matrix
		pathFinder.computeDistances(points, threshold);
		// compute all pair shortest paths using FLOYD WARSHALL
		pathFinder.allShortesPath();
		// build a complete G graph such that the nodes are the hit points
		// and the edges have a weight representing the shortest path cost
		// obtained by calculating the APSP

		// using sets to prevent duplicate egdes
		Set<Edge> completeHitPointsGraphEdges = new HashSet<>();
		for (Point source : hitPoints) {
			for (Point dist : hitPoints) {
				if (source == dist)
					continue;
				completeHitPointsGraphEdges
						.add(new Edge(source, dist, pathFinder.getDist(points.indexOf(source), points.indexOf(dist))));
			}
		}

		// compute the Minimum Spanning Tree of G
		kruskal = new Kruskal(completeHitPointsGraphEdges, hitPoints);

		// Let treeEdges(even if here it's a list but it is computed as a set)
		// be the set of edges representing the tree edges
		// calculated by Kruskal
		List<Edge> treeEdges = kruskal.computeTree();
		// now we shall construct a graph representing the actual tree computed in the
		// step above
		Graph graph = new Graph(treeEdges, hitPoints);
		// just after the graph was built, we need to traverse (BFS) current graph
		// and keep the edges we encountered, while the sum of the weights of the
		// currently kept edges does not exceed the limit or the budget
		// this is the plain English version, that was translated to code in the below method
		List<Edge> traversedEdges = graph.traversalWithLimit(1664.0, pathFinder, points, hitPoints);

		// now we acquired the set of traversed edges
		// the next step is the replace every edge in that set by
		// the full path joining both ends of the edge

		Set<Edge> replacedEdges = new HashSet<>();
		for (Edge edge : traversedEdges) {
			List<Integer> path = pathFinder.reconstructShortesPath(points.indexOf(edge.getFrom().getRoot()),
					points.indexOf(edge.getTo().getRoot()));
			for (int i = 0; i < path.size() - 1; i++) {
				replacedEdges.add(new Edge(points.get(path.get(i)), points.get(path.get(i + 1)),
						pathFinder.getDist(path.get(i), path.get(i + 1))));
			}
		}
		// as in the version with out the budget restriction, the replacement of edges
		// could eventually create new cycles, so we apply once again Kruskal to remove
		// any unwanted cycles
		System.out.println("replaces edges = " + replacedEdges.size());
		kruskal = new Kruskal(replacedEdges, points);
		treeEdges = kruskal.computeTree();
		System.out.println(treeEdges.size());
		// reconstruct the tree from the final computed tree edges, and return the root

		return new Graph(treeEdges, graph.getHitPoints()).transform();

	}

}
