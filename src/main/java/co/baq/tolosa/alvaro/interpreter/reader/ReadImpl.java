package co.baq.tolosa.alvaro.interpreter.reader;

import java.util.Optional;

public class ReadImpl implements Reader {

  private static final char UNDERSCORE = '_';
  private final int lineNumber;
  private final String line;

  private int index;
  private int currentIndex;

  private int lineLength;

  public ReadImpl( String line) {
    this(0, line);
  }

  public ReadImpl( int lineNumber,  String line) {
    this.lineNumber = lineNumber;

    this.line = line;

    lineLength = line.length();
  }

  @Override
  public Optional<Chunk> read() {
    skipWhitespaces();
    currentIndex = index;

    if (index >= lineLength) return Optional.empty();

    Optional<String> chunk = readChunk();

    return  chunk.map(val -> {
      int startingIndex = index;
      index = currentIndex;
      return Optional.of(new Chunk(val, lineNumber, startingIndex));
    }).orElseGet(this::tryToReadSpecialChar);
  }

  private Optional<Chunk> tryToReadSpecialChar() {
    if (currentIndex >= lineLength) return Optional.empty();

    char c = line.charAt(currentIndex);
    int startingIndex = currentIndex;
    currentIndex ++;
    index = currentIndex;

    return Optional.of(new Chunk(""+c, lineNumber, startingIndex));
  }

  private Optional<String> readChunk() {
    StringBuilder value = new StringBuilder();

    while(currentIndex < lineLength && (Character.isLetterOrDigit(line.charAt(currentIndex))
        || line.charAt(currentIndex) == UNDERSCORE)) {
      value.append(line.charAt(currentIndex++));
    }

    if(value.length() > 0) return Optional.of(value.toString());
    return Optional.empty();
  }

  private void skipWhitespaces() {
    while(currentIndex < lineLength && line.charAt(currentIndex) == ' ') {
      currentIndex++;
    }
    index = currentIndex;
  }
}
