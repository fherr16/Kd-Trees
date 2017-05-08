import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
  
  private Node root;
  private int count;
  
  private class Node {
    Node left;
    Node right;
    Point2D value;
    boolean compareX;
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
      root.compareX = true;
    }
    else {
      Node transverse = root;
      
      while (transverse != null) {
        if (transverse.compareX) {
          if (transverse.value.x() > p.x()) {
            if (transverse.left == null) {
              transverse.left = new Node();
              transverse.left.right = null;
              transverse.left.left = null;
              transverse.left.value = p;
              transverse.left.compareX = false; 
            }
            else transverse = transverse.left;
          }
          else {
            if (transverse.right == null) {
              transverse.right = new Node();
              transverse.right.right = null;
              transverse.right.left = null;
              transverse.right.value = p;
              transverse.right.compareX = false; 
            }
            else transverse = transverse.right;
          }
        }
        else {
          if (transverse.value.y() > p.y()) {
            if (transverse.left == null) {
              transverse.left = new Node();
              transverse.left.right = null;
              transverse.left.left = null;
              transverse.left.value = p;
              transverse.left.compareX = false; 
            }
            else transverse = transverse.left;
          }
          else {
            if (transverse.right == null) {
              transverse.right = new Node();
              transverse.right.right = null;
              transverse.right.left = null;
              transverse.right.value = p;
              transverse.right.compareX = false; 
            }
            else transverse = transverse.right;
          }
        }
      }
    }
  }
  
  public boolean contains(Point2D p) {
    return false;
  }
  
  public void draw() {
    
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    return null;
  }
  
  public Point2D nearest(Point2D p) {
    return null;
  }

}
