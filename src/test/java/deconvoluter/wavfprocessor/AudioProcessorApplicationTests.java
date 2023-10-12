package deconvoluter.wavfprocessor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AudioProcessorApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnAWaveFormWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("/waveforms/17", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(17);
		String recDate = documentContext.read("$.recDate");
		assertThat(recDate).isEqualTo("5-03-2017");
		String location = documentContext.read("$.location");
		assertThat(location).isEqualTo("windermere");
	}

	@Test
	void shouldNotReturnAWaveFromWithUnknownId() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("/waveforms/9798", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	void shouldReturnAllWaveFormsWhenAListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity("/waveforms", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void shouldCreateANewWaveForm() {
		WaveForm newWaveForm = new WaveForm(null, "27-08-2019", "loch-ness");
		ResponseEntity<Void> createResponse = restTemplate
				.postForEntity("/waveforms", newWaveForm, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfWaveForm = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity(locationOfWaveForm, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String recDate = documentContext.read("$.recDate");
		String location = documentContext.read("$.location");
		assertThat(id).isNotNull();
		assertThat(recDate).isEqualTo("27-08-2019");
		assertThat(location).isEqualTo("loch-ness");
	}


}
