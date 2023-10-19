package deconvoluter.wavfprocessor;

import org.springframework.data.annotation.Id;

public record WaveForm(@Id Long id, String recDate, String location, String owner) {
}
