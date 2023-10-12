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
    waveForm = new WaveForm(17L, "5-03-2017", "windermere");
  }

  @BeforeEach
  void setUpWaveFormArray() {
    waveForms = Arrays.array(
        new WaveForm(17L, "5-03-2017", "windermere"),
        new WaveForm(18L, "28-01-2015", "monfragüe"),
        new WaveForm(19L, "11-07-2010", "comacchio"),
        new WaveForm(20L, "12-06-2021", "biscay"));
  }

  @Test
  public void waveFormSerializationTest()  throws IOException {
    assertThat(json.write(waveForm)).isStrictlyEqualToJson("single.json");
    assertThat(json.write(waveForm)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(waveForm)).extractingJsonPathNumberValue("@.id")
        .isEqualTo(17);
    assertThat(json.write(waveForm)).hasJsonPathStringValue("@.recDate");
    assertThat(json.write(waveForm)).extractingJsonPathStringValue("@.recDate")
        .isEqualTo("5-03-2017");
    assertThat(json.write(waveForm)).hasJsonPathStringValue("@.location");
    assertThat(json.write(waveForm)).extractingJsonPathStringValue("@.location")
        .isEqualTo("windermere");
  }

  @Test
  public void waveFormDeserializationTest() throws IOException {
    String expected = """
            {
              "id" : 17,
              "recDate" : "5-03-2017",
              "location" : "windermere"
            }
            """;
    assertThat(json.parse(expected)).isEqualTo(waveForm);
    assertThat(json.parseObject(expected).id()).isEqualTo(17);
    assertThat(json.parseObject(expected).recDate()).isEqualTo("5-03-2017");
    assertThat(json.parseObject(expected).location()).isEqualTo("windermere");
  }

  @Test
  void waveFormListSerializationTest() throws IOException {
    assertThat(jsonList.write(waveForms)).isStrictlyEqualToJson("list.json");
    assertThat(json.write(waveForms[1])).hasJsonPathNumberValue("$.id");
    assertThat(json.write(waveForms[1])).extractingJsonPathNumberValue("@.id").isEqualTo(18);
    assertThat(json.write(waveForms[2])).hasJsonPathStringValue("$.recDate");
    assertThat(json.write(waveForms[2])).extractingJsonPathStringValue("@.recDate")
        .isEqualTo("11-07-2010");
  }

  @Test
  void waveFormListDeserializationTest() throws IOException {
    String expected = """
        [
          { "id" : 17, "recDate" : "5-03-2017", "location" : "windermere" },
          { "id" : 18, "recDate" : "28-01-2015", "location" : "monfragüe" },
          { "id" : 19, "recDate" : "11-07-2010", "location" : "comacchio" },
          { "id" : 20, "recDate" : "12-06-2021", "location" : "biscay" }
        ]
        """;
    assertThat(jsonList.parse(expected)).isEqualTo(waveForms);
  }
}
