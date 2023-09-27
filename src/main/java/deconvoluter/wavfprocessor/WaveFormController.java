package deconvoluter.wavfprocessor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/waveforms")
public class WaveFormController {

  @GetMapping("/{requestedId}")
  public ResponseEntity<WaveForm> findById(@PathVariable Long requestedId) {
    if (requestedId.equals(17L)) {
      WaveForm waveForm = new WaveForm(17L, "5-03-2017", "windermere");
      return ResponseEntity.ok(waveForm);
    } else {
      return ResponseEntity.notFound().build();
    }

  }
}
