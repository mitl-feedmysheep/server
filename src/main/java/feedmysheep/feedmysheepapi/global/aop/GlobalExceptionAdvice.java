package feedmysheep.feedmysheepapi.global.aop;

import feedmysheep.feedmysheepapi.global.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.response.error.ErrorEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
  @ExceptionHandler(CustomException.class)
  @ResponseBody
  public ErrorEntity handleCustomException(CustomException ex) {
    return new ErrorEntity("fail", ex.getMessage());
  }
}
