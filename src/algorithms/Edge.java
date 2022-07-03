package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class Edge implements Comparable<Edge> {
	private Tree2D from, to;
	private double distance;

	public Edge(Point from, Point to, double distance) {
		this.from = new Tree2D(from, new ArrayList<Tree2D>());
		this.to = new Tree2D(to, new ArrayList<Tree2D>());
		this.distance = distance;
	}

	public Edge(Tree2D from, Tree2D to) {
		this.from = from;
		this.to = to;
		this.distance = from.getRoot().distance(to.getRoot());
	}

	public Edge(Tree2D from, Tree2D to, double distance) {
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public Tree2D getFrom() {
		return from;
	}

	public Tree2D getTo() {
		return to;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "(" + from.getRoot() + " " + to.getRoot() + " " + distance + ")";
	}

	@Override
	public int compareTo(Edge o) {

		if (distance < o.distance)
			return -1;
		else if (distance > o.distance)
			return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge edge = (Edge) obj;
		return (from.equals(edge.from) && to.equals(edge.to)) || (to.equals(edge.from) && from.equals(edge.to));

	}

}
