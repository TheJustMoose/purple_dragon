package lexer;

import java.io.*;
import java.util.*;

public class Lexer {
  public int line = 1;
  private char peek = ' ';
  private Hashtable words = new Hashtable();

  void reserve(Word t) {
    words.put(t.lexeme, t);
  }

  public Lexer() {
    reserve(new Word(Tag.TRUE, "true"));
    reserve(new Word(Tag.FALSE, "false"));
  }

  public void Words() {
    System.out.println("\nWords:");
    Iterator<Word> it = words.values().iterator();
    while (it.hasNext()) {
      Word w = it.next();
      if (w.tag == Tag.ID)
        w.Out();
    }
    System.out.print("\nLines:");
    System.out.println(line);
  }

  private char getch() throws IOException {
    int res = System.in.read();
    if (res == -1)
      return 0;
    return (char)res;
  }

  public Token scan() throws IOException {
    while ((peek = getch()) != 0) {
      if (peek == '/') {
        peek = getch();
        if (peek == 0)
          break;

        if (peek == '/') {  // should skip // comment
          while (peek != 0 && peek != '\r')
            peek = getch();
        } else if (peek == '*') {  // should skip /* comment
          peek = getch();
          while (peek != 0 && peek != '*') {
            peek = getch();
            if (peek == '\n')
              line++;
            if (peek == '/')
              break;
          }
        } else {
          return new Token('/');
        }
      }

      if (peek == ' ' || peek == '\t' || peek == '\r')
        continue;
      else if (peek == '\n')
        line++;
      else
        break;
    }

    if (Character.isDigit(peek)) {
      int v = 0;
      do {
        v = 10*v + Character.digit(peek, 10);
        peek = getch();
      } while (Character.isDigit(peek));

      return new Num(v);
    }

    if (Character.isLetter(peek)) {
      StringBuffer b = new StringBuffer();
      do {
        b.append(peek);
        peek = getch();
      } while (Character.isLetterOrDigit(peek));
      String s = b.toString();
      Word w = (Word)words.get(s);
      if (w != null)
        return w;
      w = new Word(Tag.ID, s);
      words.put(s, w);
      return w;
    }

    Token t = new Token(peek);
    peek = ' ';
    return t;
  }
}
