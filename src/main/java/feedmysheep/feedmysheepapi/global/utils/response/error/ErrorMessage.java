package feedmysheep.feedmysheepapi.global.utils.response.error;

public class ErrorMessage {

  private static final String CS = " 고객센터에 문의해주세요.";
  public static final String PHONE_IN_USE = "이미 사용중인 번호입니다." + CS;
  public static final String CODE_GEN_TODAY_EXCEEDED = "오늘 발급받을 수 있는 인증번호 발급 횟수를 초과했어요." + CS;
  public static final String NO_VERIFICATION_CODE = "전송된 인증번호가 존재하지 않아요. 다시 발급해주세요.";
  public static final String FAIL_LOG_OVER_5_TRIES = "오늘 인증할 수 있는 횟수 5회를 초과했어요." + CS;
  public static final String OVER_3_MIN_THEN_EXPIRED = "인증번호가 유효한 3분이 지났어요. 다시 발급해주세요.";
  public static final String EMAIL_DUPLICATED = "입력하신 이메일은 현재 사용중이에요. 다른 이메일을 사용해주세요.";
  public static final String INVALID_JWT = "문제가 발생했어요. 다시 로그인 해주세요.";
  public static final String JWT_EXPIRED = "토큰이 만료되었어요.";
  public static final String NO_TOKEN = "토큰이 존재하지 않아요.";
  public static final String NO_AUTHORIZATION = "권한이 존재하지 않아요. 고객센터에 문의해주세요.";
  public static final String NO_USER_AUTHORIZATION = "유저의 권한이 존재하지 않아요. 고객센터에 문의해주세요.";
  public static final String NOT_AUTHORIZED = "접근할 수 있는 권한이 없어요. 고객센터에 문의해주세요.";
  public static final String MEMBER_NOT_FOUND = "존재하지 않는 유저에요. 고객센터에 문의해주세요.";
  public static final String NO_EMAIL_MEMBER_FOUND = "해당 이메일로 등록된 유저가 존재하지 않아요.";
  public static final String WRONG_PASSWORD = "비밀번호가 틀렸어요. 다시 시도해주세요.";
  public static final String NO_WORD_FOR_SCREENS = "해당 스크린에 말씀이 존재하지 않아요. 다시 시도해주세요.";
  public static final String NO_TEXT_FOR_SCREENS = "해당 스크린에 텍스트가 존재하지 않아요. 다시 시도해주세요.";
  public static final String NO_BODY= "부서가 존재하지 않아요. 관리자에게 문의해주세요.";
  public static final String NO_MEDIALIST_FOR_SCREENS = "해당 스크린에 미디어가 존재하지 않아요. 다시 시도해주세요.";
  public static final String NO_USER_UNDER_BODY = "해당 부서에 유저가 존재하지 않아요. 관리자에게 문의해주세요.";
  public static final String CHURCH_INVALIDATED = "교회가 비활성화 되었어요. 고객센터에 문의해주세요.";
}
