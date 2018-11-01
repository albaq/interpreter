package co.baq.tolosa.alvaro.interpreter.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class ReadImplTest {

  @Test
  void readAnInteger() {
    Reader reader = new ReadImpl("123");

    assertChunk(reader, "123", 0);
  }

  @Test
  void whenReadingTwoTokensAndThereIsOnlyOne_weShouldGetNothingOnTheSecondRead() {
    Reader reader = new ReadImpl("  this_is_one_123  ");

    assertChunk(reader, "this_is_one_123", 2);
    assertFalse(reader.read().isPresent());
  }

  @Test
  void readingIdentifierAndThenAnSpecialChar() {
    Reader reader = new ReadImpl( "var}");

    assertChunk(reader, "var", 0);
    assertChunk(reader, "}", 3);
  }

  @Test
  void readingTwoIdentifiers() {
    Reader reader = new ReadImpl( "var1 var2");

    assertChunk(reader, "var1", 0);
    assertChunk(reader, "var2", 5);
  }

  @Test
  void readingTwoIdentifiersSeparatedByAnEspecialChar() {
    Reader reader = new ReadImpl("var1}var2");

    assertChunk(reader, "var1", 0);
    assertChunk(reader, "}", 4);
    assertChunk(reader, "var2", 5);
  }

  @Test
  void readingALineOfCode() {
    Reader reader = new ReadImpl("  let a = 3; ");

    assertChunk(reader, "let", 2);
    assertChunk(reader, "a", 6);
    assertChunk(reader, "=", 8);
    assertChunk(reader, "3", 10);
    assertChunk(reader, ";", 11);
    assertFalse(reader.read().isPresent());
  }

  private void assertChunk(Reader reader, String expectedValue, int expectedColumn) {
    Optional<Chunk> chunk = reader.read();
    assertTrue(chunk.isPresent());

    Chunk token = chunk.get();

    assertEquals(expectedValue, token.getValue());
    assertEquals(expectedColumn, token.getColumnNumber());
  }
}

