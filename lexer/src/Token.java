package lexer;

public class Token {
  public final int tag;
  public Token(int t) {
    tag = t;
  }
  public void Out() {
    System.out.println(Tag.ToString(tag));
  }
}
