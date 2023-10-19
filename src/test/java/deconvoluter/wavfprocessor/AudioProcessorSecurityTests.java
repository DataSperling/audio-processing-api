package deconvoluter.wavfprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AudioProcessorSecurityTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldNotReturnAWaveFormUsingBadCredentials() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("BAD-APPLE", "nesty-1")
        .getForEntity("/waveforms/17", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    response = restTemplate
        .withBasicAuth("data-sperling", "BAD-PWORD")
        .getForEntity("/waveforms/17", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void shouldRejectUsersWhoOwnNoWaveForms() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("cuckoo-no-song", "yzg-798")
        .getForEntity("/waveforms/17", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotAllowAccessToWaveFormsTheyDoNotOwn() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("data-sperling", "nesty-1")
        .getForEntity("/waveforms/999", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }


}
