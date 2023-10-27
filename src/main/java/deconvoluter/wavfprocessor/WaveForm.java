package deconvoluter.wavfprocessor;

import org.springframework.data.annotation.Id;

public record WaveForm(@Id Long id, String recDate, String location, String owner) {
  //TODO implement PostgreSQL Date with java LocalDateTime
  //TODO implement LocalDate ordering using established pattern
  //TODO implement file handling for .mp3 .wav iOS?
}
