package deconvoluter.wavfprocessor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
//import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;


import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AudioProcessorApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnAWaveFormWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity("/waveforms/17", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(17);
		String recDate = documentContext.read("$.recDate");
		assertThat(recDate).isEqualTo("2017-03-5");
		String location = documentContext.read("$.location");
		assertThat(location).isEqualTo("windermere");
	}

	@Test
	void shouldNotReturnAWaveFromWithUnknownId() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity("/waveforms/9798", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	void shouldReturnAllWaveFormsWhenAListIsRequested() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity("/waveforms", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		int waveFormCount = documentContext.read("$.length()");
		assertThat(waveFormCount).isEqualTo(4);
		JSONArray ids = documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(17, 18, 19, 20);
		JSONArray recDates = documentContext.read("$..recDate");
		assertThat(recDates).containsExactlyInAnyOrder(
				"2017-03-5",
				"2015-01-28",
				"2010-07-11",
				"2021-06-12");
	}

	@Test
	void shouldReturnAPageOfWaveForms() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity("/waveforms?page=0&size=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnASortedPageOfWaveForms() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity("/waveforms?page=0&size=1&sort=recDate,desc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray read = documentContext.read("$[*]");
		assertThat(read.size()).isEqualTo(1);

		String recDate = documentContext.read("$[0].recDate");
		assertThat(recDate).isEqualTo("2021-06-12");
	}

	@Test
	void shouldReturnASortedPageOfWaveformsWithNoParametersUsingDefaultValues() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity("/waveforms", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(4);

		JSONArray recDates = documentContext.read("$..recDate");
		assertThat(recDates).containsExactly(
				"2021-06-12",
				"2017-03-5",
				"2015-01-28",
				"2010-07-11");
	}

	@Test
	@DirtiesContext
	void shouldCreateANewWaveForm() {
		WaveForm newWaveForm = new WaveForm(null, "5-03-2017", "windermere", null);
		ResponseEntity<Void> createResponse = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.postForEntity("/waveforms", newWaveForm, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfWaveForm = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity(locationOfWaveForm, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String recDate = documentContext.read("$.recDate");
		String location = documentContext.read("$.location");
		assertThat(id).isNotNull();
		assertThat(recDate).isEqualTo("5-03-2017");
		assertThat(location).isEqualTo("windermere");
	}

	// update waveform # 19 recorded on 2010-07-11 with new record recorded on 2018-08-12
	@Test
	@DirtiesContext
	void shouldUpdateAnExistingWaveForm() {
		WaveForm waveFormUpdate = new WaveForm(null, "2018-08-12", null, null);
		HttpEntity<WaveForm> request = new HttpEntity<>(waveFormUpdate);
		ResponseEntity<Void> response = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.exchange("/waveforms/19", HttpMethod.PUT, request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse  = restTemplate
				.withBasicAuth("data-sperling", "nesty-1")
				.getForEntity("/waveforms/19", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String recDate = documentContext.read("$.recDate");
		assertThat(id).isEqualTo(19);
		assertThat(recDate).isEqualTo("2018-08-12");
	}
}
