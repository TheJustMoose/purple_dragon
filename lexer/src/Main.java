package lexer;

import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    Lexer l = new Lexer();
    while (true) {
      Token t = l.scan();
      if (t.tag == 65535)
        break;
      //System.out.println(t.tag);
      t.Out();
    }
    System.out.println("finished.");
  }
}
