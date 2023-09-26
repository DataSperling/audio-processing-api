package deconvoluter.wavfprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class WaveformJsonTest {

  @Autowired
  private JacksonTester<WaveForm> json;

  @Test
  public void waveFormSerializationTest()  throws IOException {
    WaveForm waveForm = new WaveForm(17L, "5-03-2017", "windermere");
    assertThat(json.write(waveForm)).isStrictlyEqualToJson("single.json");
    assertThat(json.write(waveForm)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(waveForm)).extractingJsonPathNumberValue("@.id")
        .isEqualTo(17);
    assertThat(json.write(waveForm)).hasJsonPathStringValue("@.date");
    assertThat(json.write(waveForm)).extractingJsonPathStringValue("@.date")
        .isEqualTo("5-03-2017");
    assertThat(json.write(waveForm)).hasJsonPathStringValue("@.location");
    assertThat(json.write(waveForm)).extractingJsonPathStringValue("@.location")
        .isEqualTo("windermere");
  }

  @Test
  public void waveFormDeserializationTest() throws IOException {
    String expected =
        """
            {
              "id" : 17,
              "date" : "5-03-2017",
              "location" : "windermere"
            }
            """;
    assertThat(json.parse(expected)).isEqualTo(new WaveForm(17L, "5-03-2017", "windermere"));
    assertThat(json.parseObject(expected).id()).isEqualTo(17);
    assertThat(json.parseObject(expected).date()).isEqualTo("5-03-2017");
    assertThat(json.parseObject(expected).location()).isEqualTo("windermere");
  }


}
