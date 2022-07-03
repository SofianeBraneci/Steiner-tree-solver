package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class FloydWarshallPathFinder {

	private double[][] dist;
	private int[][] indecies;
	private int size;

	public FloydWarshallPathFinder(int size) {
		this.size = size;
		dist = new double[size][size];
		indecies = new int[size][size];
	}

	public void buildMatrixFromTreeEdges(int size, List<Edge> treeEdges, List<Point> points) {
		this.size = size;
		dist = new double[size][size];
		indecies = new int[size][size];
		// init
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist.length; j++) {
				dist[i][j] = Double.POSITIVE_INFINITY;
				indecies[i][j] = j;
			}
		}
		for (Edge edge : treeEdges) {
			int indexFrom = points.indexOf(edge.getFrom().getRoot()), indexTo = points.indexOf(edge.getTo().getRoot());
			if (indexFrom != -1 && indexTo != -1) {
				dist[indexFrom][indexTo] = edge.getDistance();
				dist[indexTo][indexFrom] = edge.getDistance();
			}
		}
	}

	public void computeDistances(List<Point> points, double threshold) {
		
		for (int i = 0; i < size; i++) {
			Point xPoint = points.get(i);
			for (int j = 0; j < size; j++) {
				Point yPoint = points.get(j);
				double d = xPoint.distance(yPoint);
				if (d <= threshold) {
					dist[i][j] = d;

				} else {
					dist[i][j] = Double.POSITIVE_INFINITY;
				}

			}
		}
	}

	public double getDist(int i, int j) {
		return dist[i][j];
	}

	public void allShortesPath() {
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist.length; j++) {
				if (dist[i][j] != Double.POSITIVE_INFINITY) {
					indecies[i][j] = j;
				} else {
					indecies[i][j] = -1;
				}
			}
		}
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					double d = dist[i][k] + dist[k][j];
					if (d < dist[i][j] && dist[i][k] != Double.POSITIVE_INFINITY
							&& dist[k][j] != Double.POSITIVE_INFINITY) {
						dist[i][j] = d;
						indecies[i][j] = indecies[i][k];
					}

				}

			}
			;
		}
	}

	public double[][] getDist() {
		return dist;
	}

	public int[][] getIndecies() {
		return indecies;
	}

	public List<Integer> reconstructShortesPath(int start, int end) {
		List<Integer> path = new ArrayList<>();
		if (dist[start][end] == Double.POSITIVE_INFINITY)
			return path;
		int at = start;
		path.add(at);
		while (at != end) {
			at = indecies[at][end];
			path.add(at);
		}
		System.out.println(
				String.join(" -> ", path.stream().map(Object::toString).collect(java.util.stream.Collectors.toList())));
		return path;
	}

}