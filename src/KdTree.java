import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
  
  private Node root;
  private int count;
  
  private class Node {
    Node left;
    Node right;
    Point2D value;
  }
  
  public KdTree() {
    root = null;
    count = 0;
  }
  
  public boolean isEmpty() {
    return root == null;
  }
  
  public int size() {
    return count;
  }
  
  public void insert(Point2D p) {
    if (root == null) {
      root.left = null;
      root.right = null;
      root.value = p;
    }
  }
  
  public boolean contains(Point2D p) {
    
  }
  
  public void draw() {
    
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    
  }
  
  public Point2D nearest(Point2D p) {
    
  }

}
