package deconvoluter.wavfprocessor;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class WaveformJsonTest {
  private WaveForm waveForm;
  private WaveForm[] waveForms;
  @Autowired
  private JacksonTester<WaveForm> json;
  @Autowired
  private JacksonTester<WaveForm[]> jsonList;

  @BeforeEach
  void setUpWaveForm() {
    waveForm = new WaveForm(17L, "2017-03-5", "windermere", "data-sperling");
  }

  @BeforeEach
  void setUpWaveFormArray() {
    waveForms = Arrays.array(
        new WaveForm(17L, "2017-03-5", "windermere", "data-sperling"),
        new WaveForm(18L, "2015-01-28", "monfragüe", "data-sperling"),
        new WaveForm(19L, "2010-07-11", "comacchio", "data-sperling"),
        new WaveForm(20L, "2021-06-12", "biscay", "data-sperling"));
  }

  @Test
  public void waveFormSerializationTest()  throws IOException {
    assertThat(json.write(waveForm)).isStrictlyEqualToJson("single.json");
    assertThat(json.write(waveForm)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(waveForm)).extractingJsonPathNumberValue("@.id")
        .isEqualTo(17);
    assertThat(json.write(waveForm)).hasJsonPathStringValue("@.recDate");
    assertThat(json.write(waveForm)).extractingJsonPathStringValue("@.recDate")
        .isEqualTo("2017-03-5");
    assertThat(json.write(waveForm)).hasJsonPathStringValue("@.location");
    assertThat(json.write(waveForm)).extractingJsonPathStringValue("@.location")
        .isEqualTo("windermere");
  }

  @Test
  public void waveFormDeserializationTest() throws IOException {
    String expected = """
            {
              "id" : 17,
              "recDate" : "2017-03-5",
              "location" : "windermere",
              "owner" : "data-sperling"
            }
            """;
    assertThat(json.parse(expected)).isEqualTo(waveForm);
    assertThat(json.parseObject(expected).id()).isEqualTo(17);
    assertThat(json.parseObject(expected).recDate()).isEqualTo("2017-03-5");
    assertThat(json.parseObject(expected).location()).isEqualTo("windermere");
  }

  @Test
  void waveFormListSerializationTest() throws IOException {
    assertThat(jsonList.write(waveForms)).isStrictlyEqualToJson("list.json");
    assertThat(json.write(waveForms[1])).hasJsonPathNumberValue("$.id");
    assertThat(json.write(waveForms[1])).extractingJsonPathNumberValue("@.id").isEqualTo(18);
    assertThat(json.write(waveForms[2])).hasJsonPathStringValue("$.recDate");
    assertThat(json.write(waveForms[2])).extractingJsonPathStringValue("@.recDate")
        .isEqualTo("2010-07-11");
  }

  @Test
  void waveFormListDeserializationTest() throws IOException {
    String expected = """
        [
          { "id" : 17, "recDate" : "2017-03-5", "location" : "windermere", "owner" : "data-sperling" },
          { "id" : 18, "recDate" : "2015-01-28", "location" : "monfragüe", "owner" : "data-sperling" },
          { "id" : 19, "recDate" : "2010-07-11", "location" : "comacchio", "owner" : "data-sperling" },
          { "id" : 20, "recDate" : "2021-06-12", "location" : "biscay", "owner" : "data-sperling" }
        ]
        """;
    assertThat(jsonList.parse(expected)).isEqualTo(waveForms);
  }
}
