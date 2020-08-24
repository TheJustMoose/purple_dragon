package lexer;

public class Tag {
  public final static int
    NUM = 256, ID = 257, TRUE = 258, FALSE = 259;

  public static String ToString(int t) {
    switch (t) {
      case NUM: return new String("NUM");
      case ID: return new String("ID");
      case TRUE: return new String("TRUE");
      case FALSE: return new String("FALSE");
      default: return new String("unknown");
    }
  }
}
