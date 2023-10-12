package deconvoluter.wavfprocessor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

  @GetMapping()
  public ResponseEntity<Iterable<WaveForm>> findAll() {
    return ResponseEntity.ok(waveFormRepository.findAll());
  }

  @PostMapping()
  private ResponseEntity<Void> createWaveForm(@RequestBody WaveForm newWaveFormRequest, UriComponentsBuilder ucb) {
    WaveForm savedWaveForm = waveFormRepository.save(newWaveFormRequest);
    URI locationOfNewWaveForm = ucb
        .path("waveforms/{id}")
        .buildAndExpand(savedWaveForm.id())
        .toUri();
    return ResponseEntity.created(locationOfNewWaveForm).build();
  }
}
