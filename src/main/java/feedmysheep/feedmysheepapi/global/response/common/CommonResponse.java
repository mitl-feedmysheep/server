package feedmysheep.feedmysheepapi.global.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Getter
@Setter
public class CommonResponse<T> {
  private CommonEntity common;
  @Nullable
  private T data;
}
