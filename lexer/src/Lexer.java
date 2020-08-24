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
  }

  private char getch() throws IOException {
    return (char)System.in.read();
  }

  public Token scan() throws IOException {
    for (;; peek = getch()) {
      if (peek == '/') {
        peek = getch();
        if (peek != '/')
          return new Token('/');

        // should skip comment
        while (peek != '\r')
          peek = getch();
      }

      if (peek == ' ' || peek == '\t' || peek == '\r')
        continue;
      else if (peek == '\n')
        line = line + 1;
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
