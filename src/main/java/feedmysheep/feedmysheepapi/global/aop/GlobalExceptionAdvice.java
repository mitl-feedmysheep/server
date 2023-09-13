package feedmysheep.feedmysheepapi.global.aop;

import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorEntity;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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

  @ExceptionHandler(ExpiredJwtException.class)
  public ErrorEntity handleExpiredJwtException(ExpiredJwtException ex) {
    return new ErrorEntity("invalidToken", "토큰이 만료되었어요.");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    return new ErrorEntity("fail", "Request 정보가 잘못되었어요. Swagger 문서를 확인해주세요.");
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ErrorEntity handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return new ErrorEntity("fail", "유효하지 않은 주소에요. Swagger 문서를 확인해주세요.");
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    return new ErrorEntity("fail", "메서드가 올바르지 않아요. Swagger 문서를 확인해주세요.");
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ErrorEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    return new ErrorEntity("fail", "RequestBody가 주어지지 않았어요. Swagger 문서를 확인해주세요.");
  }
}
