package co.baq.tolosa.alvaro.interpreter.reader;

import java.util.Optional;

public interface Reader {
  Optional<Chunk> read();
}
