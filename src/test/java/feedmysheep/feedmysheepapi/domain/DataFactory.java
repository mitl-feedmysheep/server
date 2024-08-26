package feedmysheep.feedmysheepapi.domain;

import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.AuthorizationScreenEntity;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
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
import java.time.LocalDateTime;
import java.util.UUID;

public class DataFactory {

  // 교회 생성
  public static ChurchEntity createChurch() {
    return ChurchEntity.builder().churchName(TestUtil.getRandomString())
        .churchLocation(TestUtil.getRandomString()).build();
  }

  // 바디 생성
  public static BodyEntity createBodyByChurchId(UUID churchId) {
    return BodyEntity.builder().churchId(churchId).bodyName(TestUtil.getRandomString())
        .bodyLocation(TestUtil.getRandomString()).build();
  }

  // 권한 생성
  public static AuthorizationEntity createAuthorization() {
    return AuthorizationEntity.builder().level(TestUtil.getRandomNum(3))
        .levelName(TestUtil.getRandomString()).build();
  }

  // 멤버 생성
  public static MemberEntity createMember(UUID authorizationId) {
    return MemberEntity.builder().authorizationId(authorizationId)
        .memberName(TestUtil.getRandomString()).sex(TestUtil.getRandomSex())
        .birthday(TestUtil.getRandomBirthday()).phone(TestUtil.getRandomPhone())
        .address(TestUtil.getRandomString()).email(TestUtil.getRandomEmail())
        .password(TestUtil.getRandomString(20)).build();
  }

  // 교회멤버매퍼 생성
  public static ChurchMemberMapEntity createChurchMemberMap(UUID churchId, UUID memberId) {
    return ChurchMemberMapEntity.builder().churchId(churchId).memberId(memberId).build();
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
  public static CellMemberMapEntity createCellMemberMapByCellIdAndMemberId(UUID cellId,
      UUID memberId) {
    return CellMemberMapEntity.builder().cellId(cellId).memberId(memberId).build();
  }

  // 셀 생성
  public static CellEntity createCellByOrganId(UUID organId) {
    return CellEntity.builder().organId(organId).cellName(TestUtil.getRandomString()).build();
  }

  // 바디멤버맵 생성
  public static BodyMemberMapEntity createBodyMemberMapByBodyIdAndMemberId(UUID bodyId,
      UUID memberId) {
    return BodyMemberMapEntity.builder().bodyId(bodyId).memberId(memberId).build();
  }

  // 올건멤버맵 생성
  public static OrganMemberMapEntity createOrganMemberMapByOrganIdAndMemberId(UUID organId,
      UUID memberId) {
    return OrganMemberMapEntity.builder().organId(organId).memberId(memberId).build();
  }

  // 올건 생성
  public static OrganEntity createOrganByBodyId(UUID bodyId) {
    return OrganEntity.builder().bodyId(bodyId).organName(TestUtil.getRandomString()).build();
  }

  // 텍스트 생성
  public static TextEntity createTextByScreenKey(String screenKey) {
    return TextEntity.builder().screenKey(screenKey).text(TestUtil.getRandomString()).build();
  }

  // 미디어 생성
  public static MediaEntity createMediaByScreenKey(String screenKey) {
    return MediaEntity.builder().screenKey(screenKey).mediaUrl(TestUtil.getRandomString()).build();
  }

  // 권한 스크린 생성
  public static AuthorizationScreenEntity createAuthorizationScreen() {
    return AuthorizationScreenEntity.builder().authorizationId(TestUtil.getRandomUUID())
        .screenKey(TestUtil.getRandomString()).build();
  }

  // 교회 모임 멤버 기도제목 생성
  public static CellGatheringMemberPrayerEntity createCellGatheringMemberPrayer(
      UUID cellGatheringMemberId) {
    return CellGatheringMemberPrayerEntity.builder().cellGatheringMemberId(cellGatheringMemberId)
        .prayerRequest(TestUtil.getRandomString()).build();
  }

  // 교회 모임 멤버 생성
  public static CellGatheringMemberEntity createCellGatheringMember(UUID cellGatheringId,
      UUID cellMemberMapId) {
    return CellGatheringMemberEntity.builder().cellGatheringId(cellGatheringId)
        .cellMemberMapId(cellMemberMapId).build();
  }

  // 교회 모임 생성
  public static CellGatheringEntity createCellGathering(UUID cellId) {
    LocalDateTime now = LocalDateTime.now();
    return CellGatheringEntity.builder().cellId(cellId).gatheringTitle(TestUtil.getRandomString())
        .gatheringDate(TestUtil.getRandomBirthday()).startedAt(now).endedAt(now.plusHours(1))
        .gatheringPlace(TestUtil.getRandomString()).build();
  }
}
