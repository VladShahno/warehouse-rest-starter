package warehouse.com.reststarter.model.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("information.disclosure.handling")
public class InformationDisclosureHandlingConfig {

  /**
   * Default error message when information disclosure handling is enabled
   */
  private String displayedMessage =
      "The server has been deserted for a while. Please be patient or try again later.";
  /**
   * Whether to change original error message to default while handling an exception
   */
  private boolean enabled = true;
}
