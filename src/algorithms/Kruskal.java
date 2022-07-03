package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Kruskal {

	private Set<Edge> edges, toKeep;
	private UnionFind unionFind;
	private double treeCost;

	public Kruskal(List<Point> points, double threshold) {
		System.out.println("Edge threshold is " + threshold);
		edges = new HashSet<>();
		toKeep = new HashSet<>();
		unionFind = new UnionFind();
		for (Point x : points) {
			unionFind.add(x);
			for (Point y : points) {
				if (x == y)
					continue;
				double dist = x.distance(y);
				if (dist <= threshold) {
					edges.add(new Edge(x, y, dist));

				}

			}
		}

		System.out.println("Total edges added " + edges.size());
	}

	public Kruskal(Set<Edge> edges, List<Point> points) {
		// TODO Auto-generated constructor stub
		this.edges = edges;
		toKeep = new HashSet<>();
		unionFind = new UnionFind();
		for (Point point : points) {
			unionFind.add(point);
		}
	}

	public List<Edge> computeTree() {
		// Krusakl basic algorithm with un
		List<Edge> sortEdges = new ArrayList<>(edges);
		Collections.sort(sortEdges, Comparator.comparingDouble(Edge::getDistance));
		for (Edge edge : sortEdges) {
//			System.out.println(unionFind.isConnected(edge.getFrom(), edge.getTo()));
			if (!unionFind.isConnected(edge.getFrom(), edge.getTo())) {
				unionFind.union(edge.getFrom(), edge.getTo());
				treeCost += edge.getDistance();
				toKeep.add(edge);
			}
		}
		System.out.println("Cost = " + treeCost);
		return new ArrayList<>(toKeep);
	}

	public double getTreeCost() {
		return treeCost;
	}

	public Kruskal(List<Point> points, List<Edge> edges) {
		this.edges = new HashSet<>(edges);
		unionFind = new UnionFind();
		for (Point x : points) {
			unionFind.add(x);

		}

	}

	public void setEdges(List<Edge> edges) {
		this.edges = new HashSet<>(edges);
	}

	public List<Edge> getTreeEdges() {
		return new ArrayList<Edge>(toKeep);
	}

	public Map<Tree2D, Integer> getTreeSizes() {
		return unionFind.getTreeSizes();
	}

	public Map<Tree2D, Tree2D> getParents() {
		return unionFind.getParents();
	}

}
