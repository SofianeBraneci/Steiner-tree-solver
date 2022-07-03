package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class UnionFind {
	private Map<Tree2D, Tree2D> parents;
	private Map<Tree2D, Integer> treeSizes;
	private int count = 0;
	public UnionFind() {

		parents = new LinkedHashMap<>();
		treeSizes = new LinkedHashMap<>();

	}

	public void add(Point p) {
		Tree2D tree2d = new Tree2D(p, new ArrayList<>());
		parents.put(tree2d, tree2d);
		treeSizes.put(tree2d, 1);
		count ++;
	}

	public void addTree(Tree2D tree2d) {
		parents.put(tree2d, tree2d);
		treeSizes.put(tree2d, 1);
		count++;

	}

	public Tree2D find(Tree2D x) {
		Tree2D root = x, next;

		while (root != parents.get(root))
			root = parents.get(root);

		while (x != root) {
			next = parents.get(x);
			parents.put(x, next);
			x = next;
		}

		return root;
	}

	public Tree2D findSimple(Tree2D x) {
		if (x == parents.get(x)) {
			return x;
		}
		return findSimple(parents.get(x));

	}

	public boolean isConnected(Tree2D source, Tree2D dist) {
//		System.out.println(source + " "+ dist);
		return findSimple(dist) == findSimple(source);
	}

	public int size(Tree2D x) {
		return treeSizes.get(x);
	}

	public void union(Tree2D x, Tree2D y) {
		Tree2D parentX = find(x), parentY = find(y);

		if (parentX == parentY)
			return;

		if (treeSizes.get(parentY) < treeSizes.get(parentX)) {
			treeSizes.put(parentX, treeSizes.get(parentY) + treeSizes.get(parentX));
			parentX.getSubTrees().add(parentY);
			parents.put(parentY, parentX);

		} else {
			treeSizes.put(parentY, treeSizes.get(parentY) + treeSizes.get(parentX));
			parentY.getSubTrees().add(parentX);
			parents.put(parentX, parentY);

		}
		count --;
	}

	public Map<Tree2D, Integer> getTreeSizes() {
		return treeSizes;
	}

	public Map<Tree2D, Tree2D> getParents() {
		return parents;
	}
	public int getCount() {
		System.err.println(count == 1);
		return count;
	}
}
