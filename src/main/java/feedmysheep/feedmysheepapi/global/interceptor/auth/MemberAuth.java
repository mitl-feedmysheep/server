package feedmysheep.feedmysheepapi.global.interceptor.auth;

import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;

public enum MemberAuth {
  MEMBER(0),
  CELL_LEADER(1),
  ORGAN_LEADER(2),
  BODY_LEADER(3),
  CHURCH_LEADER(4);

  private final int value;

  MemberAuth(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  // 정수 값을 받아 해당하는 Enum 상수를 반환하는 메서드 추가
  public static MemberAuth valueOf(int value) {
    for (MemberAuth auth : MemberAuth.values()) {
      if (auth.getValue() == value) {
        return auth;
      }
    }

    throw new CustomException(ErrorMessage.NO_AUTHORIZATION);
  }
}
