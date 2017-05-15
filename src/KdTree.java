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
      root = new Node();
      root.left = null;
      root.right = null;
      root.value = p;
      root.compareX = true;
      count++;
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
              transverse = null;
              count++;
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
              transverse = null;
              count++;
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
              transverse.left.compareX = true;
              transverse = null;
              count++;
            }
            else transverse = transverse.left;
          }
          else {
            if (transverse.right == null) {
              transverse.right = new Node();
              transverse.right.right = null;
              transverse.right.left = null;
              transverse.right.value = p;
              transverse.right.compareX = true; 
              transverse = null;
              count++;
            }
            else transverse = transverse.right;
          }
        }
      }
    }
  }
  
  public boolean contains(Point2D p) {
    
    if (p == null)
      throw new NullPointerException();
    
    if (root == null)
      return false;
    if (root.value == p)
      return true;
    
    Node transverse = root;
    
    while (transverse != null) {
      
      if (transverse.value.equals(p))
        return true;
      
      if (transverse.compareX) {
        if (transverse.value.x() > p.x()) transverse = transverse.left;
        else transverse = transverse.right;
      }
      else {
        if (transverse.value.y() > p.y()) transverse = transverse.left;
        else transverse = transverse.right;
      }
    }
    return false;
  }
  
  public void draw() {
    inOrder(root);
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    return null;
  }
  
  public Point2D nearest(Point2D p) {
    if (root == null)
      throw new NullPointerException();
    
    Point2D closest = root.value;
    Point2D leftSubTree, rightSubTree;
    
    if (p.x() < root.value.x()) {
      leftSubTree = checkLeft(root.left, closest, p);
      if (p.distanceTo(leftSubTree) < (root.value.x() - p.x())) return leftSubTree;
      rightSubTree = checkRight(root.right, closest, p);
    }
    else {
      rightSubTree = checkRight(root.right, closest, p);
      if (p.distanceTo(rightSubTree) < (p.x() - root.value.x())) return rightSubTree;
      leftSubTree = checkLeft(root.left, closest, p);
    }
        
    if (p.distanceTo(leftSubTree) < p.distanceTo(closest)) closest = leftSubTree;
    if (p.distanceTo(rightSubTree) < p.distanceTo(closest)) closest = rightSubTree;
    
    return closest;
  }
  
  private Point2D checkLeft(Node left, Point2D closest, Point2D p) {
    if (left != null) {
      if (p.distanceTo(left.value) < p.distanceTo(closest) && p.distanceTo(left.value) != 0.0) closest = left.value;
      
      Point2D leftSubTree, rightSubTree;
      
      if (left.compareX && p.x() < left.value.x()) {
        leftSubTree = checkLeft(left.left, closest, p);
        if (p.distanceTo(leftSubTree) < (left.value.x() - p.x())) return leftSubTree;
        rightSubTree = checkRight(left.right, closest, p);
      }
      else if (left.compareX && p.x() > left.value.x()){
        rightSubTree = checkRight(left.right, closest, p);
        if (p.distanceTo(rightSubTree) < (p.x() - left.value.x())) return rightSubTree;
        leftSubTree = checkLeft(left.left, closest, p);
      }
      else if (!left.compareX && p.y() < left.value.y()) {
        leftSubTree = checkLeft(left.left, closest, p);
        if (p.distanceTo(leftSubTree) < (left.value.y() - p.y())) return leftSubTree;
        rightSubTree = checkRight(left.right, closest, p);
      }
      else {
        rightSubTree = checkRight(left.right, closest, p);
        if (p.distanceTo(rightSubTree) < (p.y() - left.value.y())) return rightSubTree;
        leftSubTree = checkLeft(left.left, closest, p);
      }
      
      if (p.distanceTo(leftSubTree) < p.distanceTo(closest)) closest = leftSubTree;
      if (p.distanceTo(rightSubTree) < p.distanceTo(closest)) closest = rightSubTree;
    }
    return closest;
  }
  
  private Point2D checkRight(Node right, Point2D closest, Point2D p) {
    if (right != null) {
      if (p.distanceTo(right.value) < p.distanceTo(closest) && p.distanceTo(right.value) != 0.0) closest = right.value;
      
      Point2D leftSubTree = checkLeft(right.left, root.value, p);
      Point2D rightSubTree = checkRight(right.right, root.value, p);
      
      if (p.distanceTo(leftSubTree) < p.distanceTo(closest)) closest = leftSubTree;
      if (p.distanceTo(rightSubTree) < p.distanceTo(closest)) closest = rightSubTree;
    }
    return closest;
  }
  
  private void inOrder(Node current) {
    if (current != null) {
      inOrder(current.left);
      current.value.draw();
      inOrder(current.right);
    }
  }

}
