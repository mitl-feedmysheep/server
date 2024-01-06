package feedmysheep.feedmysheepapi.global.interceptor.auth;

public enum MemberRole {
  MEMBER(0),
  LEADER(1),
  ADMIN(2);

  private final int value;

  MemberRole(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
