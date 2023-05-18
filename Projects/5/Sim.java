import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import static java.lang.Thread.*;

public class Sim {

  public static void run_sim(MBTA mbta, Log log) {
    List<Thread> threadList = new LinkedList<>();
    Lock l = new ReentrantLock();
    Condition c	= l.newCondition();

    for (Passenger p : mbta.trip.keySet()) {
      Thread t = new Thread(() -> {
        while (true) {
          l.lock();
          while (!pcondition(mbta, p)) {
            try {
              c.await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

          Train onTrain = mbta.pstates.get(p);
          if (onTrain == null) {
            Station now = mbta.passLoc.get(p);
            Train onStation = mbta.sstates.get(now);
            BoardEvent board = new BoardEvent(p, onStation, now);
            board.replayAndCheck(mbta);
            log.passenger_boards(p, onStation, now);
          } else {
            Station now = mbta.trainLoc.get(onTrain);
            DeboardEvent deboard = new DeboardEvent(p, onTrain, now);
            deboard.replayAndCheck(mbta);
            log.passenger_deboards(p, onTrain, now);
          }
          c.signalAll();
          l.unlock();
        }
      }, p.toString());
      threadList.add(t);
    }

    for (Train train : mbta.line.keySet()) {
      Thread t = new Thread(() -> {
        while (true) {
          l.lock();
          Station now = mbta.trainLoc.get(train);
          Station next = mbta.nextStation(now, train);
          while (mbta.sstates.get(next) != null) {
            try {
              c.await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          MoveEvent move = new MoveEvent(train, now, next);
          move.replayAndCheck(mbta);
          log.train_moves(train, now, next);
          c.signalAll();
          l.unlock();
          try {
            sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }, train.toString());
      threadList.add(t);
    }

    for (Thread t : threadList) {t.start();}
    while (true) {
      try {
        mbta.checkEnd();
        for (Thread t : threadList) {
          t.stop();
        }
        return;
      } catch (Exception e) {
        try {
          sleep(2000);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      }
    }
  }






  //extra work to support "pcondition" above

  public static boolean pcondition(MBTA mbta, Passenger p) {
    boolean condition;
    Train onTrain = mbta.pstates.get(p);
    if (onTrain == null) {
      Station now = mbta.passLoc.get(p);
      Train onStation = mbta.sstates.get(now);
      BoardEvent board = new BoardEvent(p, onStation, now);
      try {
        board.replay(mbta);
        condition = true;
      } catch (Exception e) {
        condition = false;
      }
    } else {
      Station now = mbta.trainLoc.get(onTrain);
      DeboardEvent deboard = new DeboardEvent(p, onTrain, now);
      try {
        deboard.replay(mbta);
        condition = true;
      } catch (Exception e) {
        condition = false;
      }
    }
    return condition;
  }







  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("usage: ./sim <config file>");
      System.exit(1);
    }

    MBTA mbta = new MBTA();
    mbta.loadConfig(args[0]);

    Log log = new Log();

    run_sim(mbta, log);

    String s = new LogJson(log).toJson();
    PrintWriter out = new PrintWriter("log.json");
    out.print(s);
    out.close();

    mbta.reset();
    mbta.loadConfig(args[0]);
    Verify.verify(mbta, log);
  }
}
