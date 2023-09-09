package feedmysheep.feedmysheepapi.global.response.error;

public class ErrorMessage {
  public static final String PHONE_IN_USE = "이미 사용중인 번호입니다. 고객센터에 문의해주세요.";
  public static final String CODE_GEN_TODAY_EXCEEDED = "오늘 발급받을 수 있는 인증번호 발급 횟수를 초과했어요. 고객센터에 문의해주세요.";

  public static final String NO_VERIFICATION_CODE_OR_OVER_3_MIN = "전송된 인증번호가 존재하지 않거나, 유효한 시간이 지났어요. 다시 발급해주세요.";
}
