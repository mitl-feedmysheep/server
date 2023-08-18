package feedmysheep.feedmysheepapi.global.aop;

import feedmysheep.feedmysheepapi.global.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.response.error.ErrorEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
  @ExceptionHandler(CustomException.class)
  @ResponseBody
  public ErrorEntity handleCustomException(CustomException ex) {
    return new ErrorEntity("fail", ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    return new ErrorEntity("fail", "Request 정보가 잘못되었어요. Swagger 문서를 확인해주세요.");
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ErrorEntity handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return new ErrorEntity("fail", "유효하지 않은 주소에요. Swagger 문서를 확인해주세요.");
  }
}
