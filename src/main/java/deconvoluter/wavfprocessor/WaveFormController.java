package deconvoluter.wavfprocessor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/waveforms")
public class WaveFormController {

  private WaveFormRepository waveFormRepository;

  public WaveFormController(WaveFormRepository waveFormRepository) {
    this.waveFormRepository = waveFormRepository;
  }

  @GetMapping("/{requestedId}")
  public ResponseEntity<WaveForm> findById(@PathVariable Long requestedId) {
    Optional<WaveForm> waveFormOptional = waveFormRepository.findById(requestedId);
    if (waveFormOptional.isPresent()) {
      return ResponseEntity.ok(waveFormOptional.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
