package warehouse.com.reststarter.model.error;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {

  private List<String> messages = new ArrayList<>();

  public ErrorDetails(String message) {
    this.messages.add(message);
  }

  public ErrorDetails(List<String> messages) {
    this.messages.addAll(messages);
  }
}
