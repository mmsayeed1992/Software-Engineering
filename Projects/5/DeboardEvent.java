import java.util.*;

public class DeboardEvent implements Event {
  public final Passenger p; public final Train t; public final Station s;
  public DeboardEvent(Passenger p, Train t, Station s) {
    this.p = p; this.t = t; this.s = s;
  }
  public boolean equals(Object o) {
    if (o instanceof DeboardEvent e) {
      return p.equals(e.p) && t.equals(e.t) && s.equals(e.s);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(p, t, s);
  }
  public String toString() {
    return "Passenger " + p + " deboards " + t + " at " + s;
  }
  public List<String> toStringList() {
    return List.of(p.toString(), t.toString(), s.toString());
  }
  public void replayAndCheck(MBTA mbta) {
    try {
      replay(mbta);

      mbta.passLoc.put(p, s);
      mbta.pstates.put(p, null);
    } catch (Exception e) {
      throw e;
    }
  }

  //extra work to support "public void replayAndCheck(MBTA mbta)"
  public void replay(MBTA mbta) {

    if (!mbta.trip.get(p).contains(s)) {
      throw new IllegalStateException("001 DeboardEvent error");
    }

    if (mbta.trainLoc.get(t) != s) {
      throw new IllegalStateException("002 DeboardEvent error");
    }

    if (mbta.pstates.get(p) != t) {
      throw new IllegalStateException("003 DeboardEvent error3");
    }

    if (s == mbta.passLoc.get(p)) {
      throw new IllegalStateException("004 DeboardEvent error");
    }
  }
}
