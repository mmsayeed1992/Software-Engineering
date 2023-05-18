import org.junit.Test;
import java.util.Arrays;


import static org.junit.Assert.assertTrue;

public class Tests {
  @Test
  public void testPass() {
    assertTrue("true should be true", true);
  }

  @Test
  public void testVerify2() {
    MBTA mbta = new MBTA();
    mbta.loadConfig("sample.json");
    Passenger Alice = Passenger.make("Alice");
    Passenger Bob = Passenger.make("Bob");
    Train green = Train.make("green");
    Train red = Train.make("red");
  }

  @Test
  public void testVerify1() {
    MBTA mbta = new MBTA();
    Log log = new Log();
    mbta.addLine("greenLine", Arrays.asList("a", "b", "c", "f", "e"));
    Train greenLine = Train.make("greenLine");
    Station a = Station.make("a");
    Station b = Station.make("b");
    Station c = Station.make("c");
    Station d = Station.make("d");
    Station e = Station.make("e");
    Station f = Station.make("f");
    mbta.addJourney("Yongqiang", Arrays.asList("b", "f"));
    Passenger Yongqiang = Passenger.make("Yongqiang");
    log.train_moves(greenLine, a, b);
    log.passenger_boards(Yongqiang, greenLine, b);
    log.train_moves(greenLine, b, c);
    log.train_moves(greenLine, c, f);
    log.passenger_deboards(Yongqiang, greenLine, f);
    log.train_moves(greenLine, f, e);
    Verify.verify(mbta, log);
  }
}
