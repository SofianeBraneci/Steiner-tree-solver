package algorithms;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Graph {
	private Map<Tree2D, List<Tree2D>> map;
	private List<Tree2D> hitTree2ds;
	private Set<Point> hitPoints = new HashSet<>();
	private Tree2D starTree2d;

	public Graph(List<Edge> treeEdges, List<Point> terminalPoints) {
		map = new HashMap<>();
		hitTree2ds = new ArrayList<>();
		for (Point point : terminalPoints)
			hitTree2ds.add(new Tree2D(point, new ArrayList<>()));
		for (Edge edge : treeEdges) {
			if (map.containsKey(edge.getFrom())) {
				map.get(edge.getFrom()).add(edge.getTo());
			} else {
				List<Tree2D> list = new ArrayList<>();
				list.add(edge.getTo());
				map.put(edge.getFrom(), list);
			}

			if (map.containsKey(edge.getTo())) {
				map.get(edge.getTo()).add(edge.getFrom());
			} else {
				List<Tree2D> list = new ArrayList<>();
				list.add(edge.getFrom());
				map.put(edge.getTo(), list);
			}
		}

		System.out.println("Graph V = " + map.size());

	}

	public Graph(List<Point> hitPoints) {
		map = new HashMap<>();

		hitTree2ds = new ArrayList<>();
		for (Point point : hitPoints)
			hitTree2ds.add(new Tree2D(point, new ArrayList<>()));
	}

	public List<Edge> traversalWithLimit(double limit, FloydWarshallPathFinder pathFinder, List<Point> points,
			List<Point> hPoints) {
		Map<Tree2D, Boolean> visited = new HashMap<>();

		for (Tree2D tree2d : map.keySet())
			visited.put(tree2d, false);
		
		Queue<Tree2D> queue = new ArrayDeque<>();
		Tree2D root = hitTree2ds.get(0);
		queue.add(root);
		double currentCost = 0;

		Set<Edge> toKeepEdges = new HashSet<>();
		boolean stop = false;
		while (!queue.isEmpty() && !stop) {

			Tree2D current = queue.poll();
			visited.put(current, true);
			if (hPoints.contains(current.getRoot()))
				hitPoints.add(current.getRoot());

			for (Tree2D next : map.get(current)) {

				if (!visited.get(next)) {
					if (hPoints.contains(next.getRoot()))
						hitPoints.add(next.getRoot());
					visited.put(next, true);
					queue.add(next);
					double distance = pathFinder.getDist(points.indexOf(current.getRoot()),
							points.indexOf(next.getRoot()));
					if (currentCost - limit > 0.1) {
						System.out.println(
								"LIMIT REACHED CURRENT COST = " + currentCost + " edges size  = " + toKeepEdges.size());
						stop = true;
						break;
					}

					currentCost += distance;
					toKeepEdges.add(new Edge(current, next, distance));
				}
			}
		}

		return new ArrayList<>(toKeepEdges);

	}
	
	public List<Point> getHitPoints() {
		return new ArrayList<>(hitPoints);
	}

	public Tree2D transform() {
		Random genRandom = new Random();
		Map<Tree2D, Boolean> visited = new HashMap<>();
		for (Tree2D tree2d : map.keySet())
			visited.put(tree2d, false);
		Queue<Tree2D> queue = new ArrayDeque<>();
		starTree2d = hitTree2ds.get(genRandom.nextInt(hitTree2ds.size()));
		queue.add(starTree2d);

		while (!queue.isEmpty()) {
			Tree2D currentTree2d = queue.poll();
			visited.put(currentTree2d, true);
			if (map.get(currentTree2d) == null)
				System.out.println(currentTree2d.getRoot());
			;
			for (Tree2D xTree2d : map.get(currentTree2d)) {
				if (!visited.get(xTree2d)) {
					queue.add(xTree2d);
					currentTree2d.getSubTrees().add(xTree2d);
					visited.put(xTree2d, true);
				}
			}
		}
		System.out.println(starTree2d.distanceRootToSubTrees());
		return starTree2d;

	}

//	boolean bsf(Tree2D source, Tree2D dist) {
//		Map<Tree2D, Boolean> visited = new HashMap<>();
//		for (Tree2D tree2d : map.keySet())
//			visited.put(tree2d, false);
//		Queue<Tree2D> queue = new ArrayDeque<>();
//		queue.add(source);
//		while (!queue.isEmpty()) {
//			Tree2D current = queue.poll();
//			visited.put(current, true);
////			if (current == dist) {
////				System.out.println("reached-safely delete the current edge");
////			}
//
//			for (Tree2D xTree2d : map.get(current)) {
//				if (!visited.get(xTree2d)) {
//					queue.add(xTree2d);
//					visited.put(xTree2d, true);
//				}
//			}
//		}
//
//		for (Tree2D hiTree2d : hitTree2ds) {
//			if (!map.containsKey(hiTree2d))
//				continue;
//			if (!visited.get(hiTree2d))
//				return false;
//		}
//
//		return true;
//
//	}
//
//	public void removeAndCheck() {
//
//		int counter = treeEdges.size();
//		System.out.println("Start edges = " + counter);
//		for (Edge edge : treeEdges) {
//			map.get(edge.getFrom()).remove(edge.getTo());
//			if (!(bsf(edge.getFrom(), null))) {
//				map.get(edge.getFrom()).add(edge.getTo());
//			} else
//				counter--;
//		}
////		
//		System.out.println("After edges = " + counter);
//
//	}

}
