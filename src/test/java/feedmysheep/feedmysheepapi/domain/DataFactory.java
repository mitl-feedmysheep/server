package feedmysheep.feedmysheepapi.domain;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MediaEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import feedmysheep.feedmysheepapi.models.OrganEntity;
import feedmysheep.feedmysheepapi.models.OrganMemberMapEntity;
import feedmysheep.feedmysheepapi.models.TextEntity;
import feedmysheep.feedmysheepapi.models.VerificationEntity;
import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import feedmysheep.feedmysheepapi.models.WordEntity;

public class DataFactory {

  // 교회 생성
  public static ChurchEntity createChurch() {
    return ChurchEntity.builder().churchName(TestUtil.getRandomString())
        .churchLocation(TestUtil.getRandomString()).isValid(true).build();
  }

  // 바디 생성
  public static BodyEntity createBodyByChurchId(Long churchId) {
    return BodyEntity.builder().churchId(churchId).bodyName(TestUtil.getRandomString())
        .bodyLocation(TestUtil.getRandomString()).isValid(true).build();
  }

  // 권한 생성
  public static AuthorizationEntity createAuthorization() {
    return AuthorizationEntity.builder().level(TestUtil.getRandomNum(3))
        .levelName(TestUtil.getRandomString()).build();
  }

  // 멤버 생성
  public static MemberEntity createMember(Long authorizationId) {
    return MemberEntity.builder().authorizationId(authorizationId)
        .memberName(TestUtil.getRandomString()).sex(TestUtil.getRandomSex())
        .birthday(TestUtil.getRandomBirthday()).phone(TestUtil.getRandomPhone())
        .address(TestUtil.getRandomString()).email(TestUtil.getRandomEmail())
        .password(TestUtil.getRandomString(20)).build();
  }

  // 교회멤버매퍼 생성
  public static ChurchMemberMapEntity createChurchMemberMap(Long churchId, Long memberId) {
    return ChurchMemberMapEntity.builder().churchId(churchId).memberId(memberId).isValid(true)
        .build();
  }

  // 인증실패로그 생성 (by phone: 예외적)
  public static VerificationFailLogEntity createVerificationFailLogByPhone(String phone) {
    return VerificationFailLogEntity.builder().phone(phone)
        .verificationCode(TestUtil.getRandomString(6)).build();
  }

  // 인증코드 생성
  public static VerificationEntity createVerificationByPhone(String phone) {
    return VerificationEntity.builder().phone(phone).verificationCode(TestUtil.getRandomString(6))
        .build();
  }

  // 말씀 생성
  public static WordEntity createWordByScreenKey(String screenKey) {
    return WordEntity.builder().screenKey(screenKey).words(TestUtil.getRandomString()).build();
  }

  // 셀멤버맵 생성
  public static CellMemberMapEntity createCellMemberMapByCellIdAndMemberId(Long cellId,
      Long memberId) {
    return CellMemberMapEntity.builder().cellId(cellId).memberId(memberId).isValid(true).build();
  }

  // 셀 생성
  public static CellEntity createCellByOrganId(Long organId) {
    return CellEntity.builder().organId(organId).cellName(TestUtil.getRandomString()).isValid(true)
        .build();
  }

  // 바디멤버맵 생성
  public static BodyMemberMapEntity createBodyMemberMapByBodyIdAndMemberId(Long bodyId,
      Long memberId) {
    return BodyMemberMapEntity.builder().bodyId(bodyId).memberId(memberId).isValid(true).build();
  }

  // 올건멤버맵 생성
  public static OrganMemberMapEntity createOrganMemberMapByOrganIdAndMemberId(Long organId,
      Long memberId) {
    return OrganMemberMapEntity.builder().organId(organId).memberId(memberId).isValid(true).build();
  }

  // 올건 생성
  public static OrganEntity createOrganByBodyId(Long bodyId) {
    return OrganEntity.builder().bodyId(bodyId).organName(TestUtil.getRandomString()).isValid(true)
        .build();
  }

  // 텍스트 생성
  public static TextEntity createTextByScreenKey(String screenKey) {
    return TextEntity.builder().screenKey(screenKey).text(TestUtil.getRandomString()).build();
  }

  // 미디어 생성
  public static MediaEntity createMediaByScreenKey(String screenKey) {
    return MediaEntity.builder().screenKey(screenKey).mediaUrl(TestUtil.getRandomString()).build();
  }
}
