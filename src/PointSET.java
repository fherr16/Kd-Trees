import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
  
  private SET<Point2D> set;
  
  private class RectIterator implements Iterator<Point2D> {
    private Vector<Point2D> list;
    
    private RectIterator(RectHV rect) {
      if (set.isEmpty()) throw new NullPointerException();
      if (rect == null) throw new NullPointerException();

      list = new Vector<Point2D>();
      
      for (Point2D p : set)
        if (rect.contains(p))
          list.add(p); 
    }
    
    public boolean hasNext() {
      return !list.isEmpty();
    }
    
    public Point2D next() {
      if (list.isEmpty()) throw new NoSuchElementException();
      return list.remove(list.size() - 1);
    }
  }
  
  private class RectIterable implements Iterable<Point2D> {
    private RectHV rect;
    public RectIterable(RectHV rect) {
      if (rect == null) throw new NullPointerException();
      this.rect = rect;
    }
    public Iterator<Point2D> iterator() {
      return new RectIterator(rect);
    }
  }
  
  public PointSET() {
    set = new SET<Point2D>();
  }
  
  public boolean isEmpty() {
    return set.isEmpty();
  }
  
  public int size() {
    return set.size();
  }
  
  public void insert(Point2D p) {
    if (p == null) throw new NullPointerException();
    if (!set.contains(p)) set.add(p);
  }
  
  public boolean contains(Point2D p) {
    if (p == null) throw new NullPointerException();
    return set.contains(p);
  }
  
  public void draw() {
    for (Point2D p: set) p.draw();
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) throw new NullPointerException();
    return new RectIterable(rect);
  }
  
  public Point2D nearest(Point2D p) {
    if (p == null) throw new NullPointerException();
    if (set.isEmpty()) throw new NoSuchElementException();
    
    Point2D closest = set.min();
    for (Point2D point : set)
      if (p.distanceTo(point) < p.distanceTo(closest))
        closest = point;
    return closest;
  }
  
}
