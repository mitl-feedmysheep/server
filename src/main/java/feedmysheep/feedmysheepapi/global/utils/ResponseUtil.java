package feedmysheep.feedmysheepapi.global.utils;

import feedmysheep.feedmysheepapi.global.dto.response.Common;
import feedmysheep.feedmysheepapi.global.dto.response.CommonResponse;

public class ResponseUtil {
  public static <T> CommonResponse<T> success(T response) {
    Common common = new Common("success", null);
    return new CommonResponse<> (common, response);
  }

  public static CommonResponse<?> error(String errorMessage) {
    Common common = new Common("fail", errorMessage);
    return new CommonResponse<> (common, null);
  }
}
