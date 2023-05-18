import java.util.*;

abstract public class Piece {
    public Color clr;
    private static HashMap<Character, PieceFactory> facts = new HashMap<>();

    public static void registerPiece(PieceFactory pf) {
        facts.put(new Character(pf.symbol()), pf);
    }

    public static Piece createPiece(String name) {
        char color = name.charAt(0);
        char type = name.charAt(1);
        Color c;
        if (color == 'b') {
            c = Color.BLACK;
        } else if (color == 'w') {
            c = Color.WHITE;
        } else {
            throw new UnsupportedOperationException();
        }
        return facts.get(new Character(type)).create(c);
    }

    public Color color() {
        // You should write code here and just inherit it in
        // subclasses. For this to work, you should know
        // that subclasses can access superclass fields.
        return clr;
    }

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);
}
