package warehouse.com.reststarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("warehouse.com")
public class RestStarterApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestStarterApplication.class, args);
  }

}
