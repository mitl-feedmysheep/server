package feedmysheep.feedmysheepapi.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Getter
@Setter
public class CommonResponse<T> {
  private Common common;
  @Nullable
  private T data;
}
