package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class Tree2D {
  private Point root;
  private ArrayList<Tree2D> subtrees;

  public Tree2D (Point p, ArrayList<Tree2D> trees){
    this.root=p;
    this.subtrees=trees;
  }
  public Point getRoot(){
    return this.root;
  }
  public ArrayList<Tree2D> getSubTrees(){
    return this.subtrees;
  }
  public double distanceRootToSubTrees(){
    double d=0;
    for (int i=0;i<this.subtrees.size();i++) d+=subtrees.get(i).getRoot().distance(root);
    return d;
  }
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((root == null) ? 0 : root.hashCode());
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
	Tree2D other = (Tree2D) obj;
	if (root == null) {
		if (other.root != null)
			return false;
	} else if (!root.equals(other.root))
		return false;
	return true;
}
public Tree2D copy() {
	return new Tree2D(root, subtrees);
}
}
