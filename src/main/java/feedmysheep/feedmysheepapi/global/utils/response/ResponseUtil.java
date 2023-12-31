package feedmysheep.feedmysheepapi.global.utils.response;

import feedmysheep.feedmysheepapi.global.utils.response.common.CommonEntity;
import feedmysheep.feedmysheepapi.global.utils.response.common.CommonResponse;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorEntity;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorResponse;

public class ResponseUtil {
  public static <T> CommonResponse<T> success(T response) {
    CommonEntity common = new CommonEntity("success", "정상적인 응답입니다.");
    return new CommonResponse<> (common, response);
  }

  public static ErrorResponse error(String errorMessage) {
    ErrorEntity error = new ErrorEntity("fail", errorMessage);
    return new ErrorResponse(error);
  }
}
