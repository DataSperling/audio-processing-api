package deconvoluter.wavfprocessor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

		String date = documentContext.read("$.date");
		assertThat(date).isEqualTo("19-07-2022");

		String location = documentContext.read("$.location");
		assertThat(location).isEqualTo("loch-ness");
	}


}
