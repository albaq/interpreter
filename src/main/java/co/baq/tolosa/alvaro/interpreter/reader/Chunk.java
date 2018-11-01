package co.baq.tolosa.alvaro.interpreter.reader;

public class Chunk {
  private final String value;
  private final int lineNumber;
  private final int columnNumber;

  public Chunk(String value, int lineNumber, int columnNumber) {
    this.value = value;
    this.lineNumber = lineNumber;
    this.columnNumber = columnNumber;
  }

  public String getValue() {
    return value;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }
}
