package feedmysheep.feedmysheepapi.domain;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;

public class DataFactory {

  // 교회 생성
  public static ChurchEntity createChurch() {
    return ChurchEntity.builder()
        .churchName(TestUtil.getRandomString())
        .churchLocation(TestUtil.getRandomString())
        .build();
  }

  // 바디 생성
  public static BodyEntity createBodyByChurchId(Long churchId) {
    return BodyEntity.builder()
        .churchId(churchId)
        .bodyName(TestUtil.getRandomString())
        .bodyLocation(TestUtil.getRandomString())
        .build();
  }

  // 권한 생성
  public static AuthorizationEntity createAuthorization() {
    return AuthorizationEntity.builder()
        .level(TestUtil.getRandomNum(3))
        .levelName(TestUtil.getRandomString())
        .build();
  }

  // 멤버 생성
  public static MemberEntity createMember(Long authorizationId) {
    return MemberEntity.builder()
        .authorizationId(authorizationId)
        .memberName(TestUtil.getRandomString())
        .sex(TestUtil.getRandomSex())
        .birthday(TestUtil.getRandomBirthday())
        .phone(TestUtil.getRandomPhone())
        .address(TestUtil.getRandomString())
        .email(TestUtil.getRandomEmail())
        .password(TestUtil.getRandomString(20))
        .build();
  }

  // 교회멤버매퍼 생성
  public static ChurchMemberMapEntity createChurchMemberMap(Long churchId, Long memberId) {
    return ChurchMemberMapEntity.builder()
        .churchId(churchId)
        .memberId(memberId)
        .build();
  }

  // 인증실패로그 생성 (by phone: 예외적)
  public static VerificationFailLogEntity createVerificationFailLogByPhone(String phone) {
    return VerificationFailLogEntity.builder()
        .phone(phone)
        .verificationCode(TestUtil.getRandomString(6))
        .build();
  }
}
