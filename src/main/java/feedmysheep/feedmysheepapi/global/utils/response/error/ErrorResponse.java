package feedmysheep.feedmysheepapi.global.utils.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
  private ErrorEntity common;
}
