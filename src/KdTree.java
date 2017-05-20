import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
  
  private Node root;
  private int count;
  
  private class Node {
    private Node left;
    private Node right;
    private Point2D value;
    private boolean compareX;
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
    if (p == null) throw new NullPointerException();
    if (contains(p)) return;
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
    if (rect == null) throw new NullPointerException();
    return new RectIterable(rect);
  }
  
  private class RectIterable implements Iterable<Point2D> {
    private RectHV rect;
    public RectIterable(RectHV rect) {
      this.rect = rect;
    }
    public Iterator<Point2D> iterator() {
      return new RectIterator(rect);
    }
  }
  
  private class RectIterator implements Iterator<Point2D> {
    
    private Vector<Point2D> points;
    private RectHV rect;
    
    private RectIterator(RectHV rect) {
      
      if (root == null) throw new NullPointerException();
      
      this.rect = rect;
      this.points = new Vector<Point2D>();
      
      if (rect.xmax() < root.value.x()) checkLeft(root.left);
      else if (rect.xmin() > root.value.x()) checkRight(root.right);
      else {
        if (rect.contains(root.value)) points.add(root.value);
        checkLeft(root.left);
        checkRight(root.right);
      }
    }
    
    private void checkLeft(Node left) {
      if (left != null) {
        if (rect.contains(left.value)) points.add(left.value);
        
        if (left.compareX && rect.xmax() < left.value.x()) checkLeft(left.left);
        else if (left.compareX && rect.xmin() > left.value.x()) checkRight(left.right);
        else if (!left.compareX && rect.ymax() < left.value.y()) checkLeft(left.left);
        else if (!left.compareX && rect.ymin() > left.value.y()) checkRight(left.right);
        else {
          checkLeft(left.left);
          checkRight(left.right);
        }
      }
    }
    
    private void checkRight(Node right) {
      if (right != null) {
        if (rect.contains(right.value)) points.add(right.value);
        
        if (right.compareX && rect.xmax() < right.value.x()) checkLeft(right.left);
        else if (right.compareX && rect.xmin() > right.value.x()) checkRight(right.right);
        else if (!right.compareX && rect.ymax() < right.value.y()) checkLeft(right.left);
        else if (!right.compareX && rect.ymin() > right.value.y()) checkRight(right.right);
        else {
          checkLeft(right.left);
          checkRight(right.right);
        }
      }
    }
    
    public boolean hasNext() {
      return !points.isEmpty();
    }
    
    public Point2D next() {
      if (points.isEmpty()) throw new NoSuchElementException();
      return points.remove(0);
    }
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
      if (p.distanceTo(left.value) < p.distanceTo(closest)) closest = left.value;
      
      Point2D leftSubTree, rightSubTree;
      
      if (left.compareX && p.x() < left.value.x()) {
        leftSubTree = checkLeft(left.left, closest, p);
        if (p.distanceTo(leftSubTree) < (left.value.x() - p.x())) return leftSubTree;
        rightSubTree = checkRight(left.right, closest, p);
      }
      else if (left.compareX && p.x() > left.value.x()) {
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
      if (p.distanceTo(right.value) < p.distanceTo(closest)) closest = right.value;
      
      Point2D leftSubTree, rightSubTree;
      
      if (right.compareX && p.x() < right.value.x()) {
        leftSubTree = checkLeft(right.left, closest, p);
        if (p.distanceTo(leftSubTree) < (right.value.x() - p.x())) return leftSubTree;
        rightSubTree = checkRight(right.right, closest, p);
      }
      else if (right.compareX && p.x() > right.value.x()) {
        rightSubTree = checkRight(right.right, closest, p);
        if (p.distanceTo(rightSubTree) < (p.x() - right.value.x())) return rightSubTree;
        leftSubTree = checkLeft(right.left, closest, p);
      }
      else if (!right.compareX && p.y() < right.value.y()) {
        leftSubTree = checkLeft(right.left, closest, p);
        if (p.distanceTo(leftSubTree) < (right.value.y() - p.y())) return leftSubTree;
        rightSubTree = checkRight(right.right, closest, p);
      }
      else {
        rightSubTree = checkRight(right.right, closest, p);
        if (p.distanceTo(rightSubTree) < (p.y() - right.value.y())) return rightSubTree;
        leftSubTree = checkLeft(right.left, closest, p);
      }
      
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
