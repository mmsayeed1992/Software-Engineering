import java.util.*;

public class Knight extends Piece {
    public Knight(Color c) {
        if (c == Color.BLACK || c == Color.WHITE) {
            clr = c;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    // implement appropriate methods

    public String toString() {
        if (clr == Color.BLACK) {
            return "bn";
        } else {
            return "wn";
        }
    }

    public List<String> moves(Board b, String loc) {
        List<String> toLoc = new ArrayList<String>();
        int file = loc.charAt(0);
        int rank = loc.charAt(1);

        int[] h = {2, 2, 1, 1, -2, -2, -1, -1};
        int[] v = {1, -1, 2, -2, 1, -1, 2, -2};

        for (int i = 0; i < 8; i++) {
            int f = file + h[i];
            int r = rank + v[i];
            String move = "" + (char)f + (char)r;
            try {
                Piece p = b.getPiece(move);
                if (p == null) {
                    toLoc.add(move);
                } else {
                    if (p.color() != clr) {
                        toLoc.add(move);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
        return toLoc;
    }
}
