import java.util.Iterator;
import java.util.Vector;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
  
  SET<Point2D> set;
  
  private class RectIterator implements Iterator<Point2D> {
    private Vector<Point2D> list;
    
    private RectIterator(RectHV rect) {
      if (set.isEmpty())
        throw new NullPointerException();
      
      for (Point2D p : set)
        if (rect.contains(p))
          list.add(p);
      
    }
    
    public boolean hasNext() {
      return !list.isEmpty();
    }
    
    public Point2D next() {
      return list.remove(list.size() - 1);
    }
  }
  
  public class RectIterable implements Iterable<Point2D> {
    private RectHV rect;
    public RectIterable(RectHV rect) {
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
    if (p == null)
      throw new NullPointerException();
    
    if (!set.contains(p)) set.add(p);
  }
  
  public boolean contains(Point2D p) {
    if (p == null)
      throw new NullPointerException();
    
    return set.contains(p);
  }
  
  public void draw() {
    for (Point2D p: set) p.draw();
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new NullPointerException();
    
    return new RectIterable(rect);
  }
  
  public Point2D nearest(Point2D p) {
    if (p == null)
      throw new NullPointerException();
    
    Point2D closest = null;
    for (Point2D point : set)
      if (p.distanceTo(point) < p.distanceTo(closest))
        closest = point;
    return closest;
  }
  
}
