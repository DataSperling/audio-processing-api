package deconvoluter.wavfprocessor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/waveforms")
public class WaveFormController {

  //TODO implement PUT for ML API

  private WaveFormRepository waveFormRepository;

  public WaveFormController(WaveFormRepository waveFormRepository) {
    this.waveFormRepository = waveFormRepository;
  }

  @GetMapping("/{requestedId}")
  public ResponseEntity<WaveForm> findById(@PathVariable Long requestedId, Principal principal) {
    Optional<WaveForm> waveFormOptional = Optional
        .ofNullable(waveFormRepository.findByIdAndOwner(requestedId, principal.getName()));
    if (waveFormOptional.isPresent()) {
      return ResponseEntity.ok(waveFormOptional.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<WaveForm>> findAll(Pageable pageable, Principal principal) {
    Page<WaveForm> page = waveFormRepository.findByOwner(principal.getName(),
        PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.DESC, "recDate"))
    ));
    return ResponseEntity.ok(page.getContent());
  }

  @PostMapping
  private ResponseEntity<Void> createWaveForm(
      @RequestBody WaveForm newWaveFormRequest,
      UriComponentsBuilder ucb,
      Principal principal) {
    WaveForm waveFormWithOwner = new WaveForm(
        null, newWaveFormRequest.recDate(),
        newWaveFormRequest.location(),
        principal.getName());
    WaveForm savedWaveForm = waveFormRepository.save(waveFormWithOwner);
    URI locationOfNewWaveForm = ucb
        .path("waveforms/{id}")
        .buildAndExpand(savedWaveForm.id())
        .toUri();
    return ResponseEntity.created(locationOfNewWaveForm).build();
  }
}
