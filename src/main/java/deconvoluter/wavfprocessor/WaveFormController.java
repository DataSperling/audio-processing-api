package deconvoluter.wavfprocessor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/waveforms")
public class WaveFormController {

  @GetMapping("/{requestedId}")
  public ResponseEntity<WaveForm> findById() {
    WaveForm waveForm = new WaveForm(17L, "19-07-2022", "loch-ness");
    return ResponseEntity.ok(waveForm);
  }
}
