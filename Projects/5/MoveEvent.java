import java.util.*;

public class MoveEvent implements Event {
  public final Train t; public final Station s1, s2;
  public MoveEvent(Train t, Station s1, Station s2) {
    this.t = t; this.s1 = s1; this.s2 = s2;
  }
  public boolean equals(Object o) {
    if (o instanceof MoveEvent e) {
      return t.equals(e.t) && s1.equals(e.s1) && s2.equals(e.s2);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(t, s1, s2);
  }
  public String toString() {
    return "Train " + t + " moves from " + s1 + " to " + s2;
  }
  public List<String> toStringList() {
    return List.of(t.toString(), s1.toString(), s2.toString());
  }
  public void replayAndCheck(MBTA mbta) {
    try {
      replay(mbta);

      int index = mbta.line.get(t).lastIndexOf(s1);
      int nextIndex = index + mbta.trainDir.get(t);
      if (nextIndex < 0 || nextIndex >= mbta.line.get(t).size()) {
        mbta.trainDir.put(t, mbta.trainDir.get(t) * -1);
      }
      mbta.trainLoc.put(t, s2);
      mbta.sstates.put(s1, null);
      mbta.sstates.put(s2, t);
    } catch (Exception e) {
      throw e;
    }
  }

  //extra work to support "public void replayAndCheck(MBTA mbta)"
  public void replay(MBTA mbta) {

    Station start = mbta.trainLoc.get(t);
    if(start != s1) {
      throw new IllegalStateException("001 MoveEvent error");
    }

    if (mbta.nextStation(s1, t) != s2) {
      throw new IllegalStateException("002 MoveEvent error");
    }

    if (mbta.sstates.get(s2) != null) {
      throw new IllegalStateException("003 MoveEvent error");
    }
  }
}
