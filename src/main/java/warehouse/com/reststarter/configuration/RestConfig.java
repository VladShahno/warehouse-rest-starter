package warehouse.com.reststarter.configuration;


import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import warehouse.com.reststarter.model.error.InformationDisclosureHandlingConfig;
import warehouse.com.reststarter.model.error.RestErrorAttributes;

@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties(InformationDisclosureHandlingConfig.class)
@Configuration(proxyBeanMethods = false)
public class RestConfig {

  @Bean
  @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
  public RestErrorAttributes restErrorAttributes(
      InformationDisclosureHandlingConfig informationDisclosureHandlingConfig) {
    return new RestErrorAttributes(informationDisclosureHandlingConfig);
  }
}
