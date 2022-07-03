package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class DefaultTeam {

	public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		SteinerSolver solver = new SteinerSolver(points, hitPoints);
		return solver.solve(edgeThreshold);

	}

	public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		SteinerSolver solver = new SteinerSolver(points, hitPoints);
		return solver.solveBudget(edgeThreshold);
	}
}
