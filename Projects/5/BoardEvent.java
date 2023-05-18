import java.util.*;

public class BoardEvent implements Event {
  public final Passenger p; public final Train t; public final Station s;
  public BoardEvent(Passenger p, Train t, Station s) {
    this.p = p; this.t = t; this.s = s;
  }
  public boolean equals(Object o) {
    if (o instanceof BoardEvent e) {
      return p.equals(e.p) && t.equals(e.t) && s.equals(e.s);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(p, t, s);
  }
  public String toString() {
    return "Passenger " + p + " boards " + t + " at " + s;
  }
  public List<String> toStringList() {
    return List.of(p.toString(), t.toString(), s.toString());
  }

  public void replayAndCheck(MBTA mbta) {
    try {
      replay(mbta);
      mbta.pstates.put(p, t);
    } catch (Exception e) {
      throw e;
    }
  }


  //extra work to support "public void replayAndCheck(MBTA mbta)"
  public void replay(MBTA mbta) {
    if (!mbta.trip.get(p).contains(s)) {
      throw new IllegalStateException("001 BoardEvent error");
    }

    if (mbta.trainLoc.get(t) != s || mbta.passLoc.get(p) != s) {
      throw new IllegalStateException("002 BoardEvent error");
    }

    int index = mbta.trip.get(p).lastIndexOf(s);
    if (index == mbta.trip.get(p).size() - 1) {
      throw new IllegalStateException("BoardEvent error, last station!");
    }
    Station destination = mbta.trip.get(p).get(index + 1);
    if (!mbta.line.get(t).contains(destination)) {
      throw new IllegalStateException("003 BoardEvent error");
    }

    if (mbta.pstates.get(p) != null) {
      throw new IllegalStateException("004 board event error");
    }
  }
}
