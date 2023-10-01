package warehouse.com.reststarter.model.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  private List<ErrorDetail> errorDetails;

  @AllArgsConstructor
  @Data
  @Builder
  public static class ErrorDetail {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
  }
}
