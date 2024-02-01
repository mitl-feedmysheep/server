package feedmysheep.feedmysheepapi.domain.member.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.BodyMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.BodyRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.ChurchRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.OrganMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.church.app.repository.OrganRepository;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberMapper;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberReqDto;
import feedmysheep.feedmysheepapi.domain.member.app.dto.MemberResDto;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationFailLogRepository;
import feedmysheep.feedmysheepapi.domain.verification.app.repository.VerificationRepository;
import feedmysheep.feedmysheepapi.global.interceptor.auth.MemberAuth;
import feedmysheep.feedmysheepapi.global.policy.CONSTANT.SOLAPI;
import feedmysheep.feedmysheepapi.global.policy.CONSTANT.VERIFICATION;
import feedmysheep.feedmysheepapi.global.utils.Util;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtDto.memberInfo;
import feedmysheep.feedmysheepapi.global.utils.jwt.JwtTokenProvider;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.BodyEntity;
import feedmysheep.feedmysheepapi.models.BodyMemberMapEntity;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import feedmysheep.feedmysheepapi.models.OrganEntity;
import feedmysheep.feedmysheepapi.models.OrganMemberMapEntity;
import feedmysheep.feedmysheepapi.models.VerificationEntity;
import feedmysheep.feedmysheepapi.models.VerificationFailLogEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final VerificationRepository verificationRepository;
  private final VerificationFailLogRepository verificationFailLogRepository;
  private final AuthorizationRepository authorizationRepository;
  private final DefaultMessageService messageService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final ChurchMemberMapRepository churchMemberMapRepository;
  private final MemberMapper memberMapper;
  private final ChurchRepository churchRepository;
  private final BodyRepository bodyRepository;
  private final BodyMemberMapRepository bodyMemberMapRepository;
  private final OrganRepository organRepository;
  private final OrganMemberMapRepository organMemberMapRepository;
  private final CellRepository cellRepository;
  private final CellMemberMapRepository cellMemberMapRepository;

  @Autowired
  public MemberService(MemberRepository memberRepository,
      VerificationRepository verificationRepository,
      VerificationFailLogRepository verificationFailLogRepository,
      AuthorizationRepository authorizationRepository,
      ChurchMemberMapRepository churchMemberMapRepository, PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider, MemberMapper memberMapper,
      ChurchRepository churchRepository, BodyRepository bodyRepository,
      BodyMemberMapRepository bodyMemberMapRepository, OrganRepository organRepository,
      OrganMemberMapRepository organMemberMapRepository, CellRepository cellRepository,
      CellMemberMapRepository cellMemberMapRepository) {
    this.memberRepository = memberRepository;
    this.verificationRepository = verificationRepository;
    this.verificationFailLogRepository = verificationFailLogRepository;
    this.authorizationRepository = authorizationRepository;
    this.churchMemberMapRepository = churchMemberMapRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.memberMapper = memberMapper;
    this.churchRepository = churchRepository;
    this.bodyRepository = bodyRepository;
    this.bodyMemberMapRepository = bodyMemberMapRepository;
    this.organRepository = organRepository;
    this.organMemberMapRepository = organMemberMapRepository;
    this.cellRepository = cellRepository;
    this.cellMemberMapRepository = cellMemberMapRepository;
    this.messageService = NurigoApp.INSTANCE.initialize(SOLAPI.API_KEY, SOLAPI.API_SECRET_KEY,
        SOLAPI.DOMAIN);
  }

  ;

  public void sendVerificationCode(MemberReqDto.sendVerificationCode query) {
    String phone = query.getPhone();
    LocalDate today = LocalDate.now();
    LocalDateTime startOfToday = today.atTime(LocalTime.MIN);
    LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

    // 1. 휴대폰 사용 여부 체크
    this.memberRepository.getMemberByPhone(phone).ifPresent((member -> {
      throw new CustomException(ErrorMessage.PHONE_IN_USE);
    }));

    // 2. FailLog 5회 이상 여부 체크
    int failCount = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(phone,
        startOfToday, endOfToday);
    if (failCount >= 5) {
      throw new CustomException(ErrorMessage.FAIL_LOG_OVER_5_TRIES);
    }

    // 3. 인증코드 발급 5회 미만 여부 체크
    int usedCount = this.verificationRepository.countByPhoneAndValidDate(phone, today);
    if (usedCount >= VERIFICATION.MAX_CODE_GEN_NUM) {
      throw new CustomException(ErrorMessage.CODE_GEN_TODAY_EXCEEDED);
    }

    // 4. 인증코드 generate
    Random random = new Random();
    int min = 100000;
    int max = 999999;
    String verificationCode = Integer.toString(random.nextInt(max - min + 1) + min);

    // 5. 인증코드 전송
    String messageBody = "[피마쉽(FeedMySheep)] 인증번호는 " + verificationCode + "입니다.";
    try {
      Message message = new Message();
      message.setFrom(SOLAPI.FROM_PHONE_NUMBER);
      message.setTo(phone);
      message.setText(messageBody);

      this.messageService.sendOne(new SingleMessageSendingRequest(message));
    } catch (Exception e) {
      System.out.println(e);
      // TODO 슬랙 메시지

      // 문자 메시지 전송 에러
      throw new CustomException(e.getMessage());
    }

    // 6. 인증코드 DB 저장
    VerificationEntity verification = VerificationEntity.builder().phone(phone)
        .verificationCode(verificationCode).build();

    this.verificationRepository.save(verification);
  }

  public void checkVerificationCode(MemberReqDto.checkVerificationCode query) {
    String phone = query.getPhone();
    String code = query.getCode();
    LocalDateTime startOfToday = LocalDate.now().atTime(LocalTime.MIN);
    LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);

    // 1. 휴대폰 사용 여부 체크
    this.memberRepository.getMemberByPhone(phone).ifPresent(member -> {
      throw new CustomException(ErrorMessage.PHONE_IN_USE);
    });

    // 2. 금일 인증실패 5회 여부 체크
    int failCount = this.verificationFailLogRepository.countByPhoneAndCreatedAtBetween(phone,
        startOfToday, endOfToday);
    if (failCount >= VERIFICATION.MAX_CODE_TRY_NUM) {
      throw new CustomException(ErrorMessage.FAIL_LOG_OVER_5_TRIES);
    }

    // 3. 휴대폰 번호와 인증코드 여부 체크
    VerificationEntity verification = this.verificationRepository.getVerificationByPhoneAndVerificationCode(
        phone, code).orElseThrow(() -> new CustomException(ErrorMessage.NO_VERIFICATION_CODE));
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime threeMinLater = verification.getCreatedAt().plusMinutes(3);
    // - 존재하지 않는다면, 인증실패 저장 후 재시도 요청
    if (now.isAfter(threeMinLater)) {
      VerificationFailLogEntity failLog = VerificationFailLogEntity.builder().phone(phone)
          .verificationCode(code).build();
      this.verificationFailLogRepository.save(failLog);
      throw new CustomException(ErrorMessage.OVER_3_MIN_THEN_EXPIRED);
    }
  }

  public void checkEmailDuplication(MemberReqDto.checkEmailDuplication query) {
    String email = query.getEmail();

    this.memberRepository.getMemberByEmail(email).ifPresent(member -> {
      throw new CustomException(ErrorMessage.EMAIL_DUPLICATED);
    });
  }

  @Transactional
  public MemberResDto.signUp signUp(MemberReqDto.signUp body) {
    // 0. 만 14세 이상 검증
    LocalDate today = LocalDate.now();
    boolean isUnder14 = body.getBirthday().plusYears(14).isAfter(today);
    if (isUnder14) {
      throw new CustomException(ErrorMessage.UNDER_14);
    }

    // 1. 비밀번호 암호화
    body.setPassword(this.passwordEncoder.encode(body.getPassword()));

    // 2. Validation - 방어로직
    this.memberRepository.getMemberByPhone(body.getPhone()).ifPresent(member -> {
      throw new CustomException(ErrorMessage.PHONE_IN_USE);
    });
    this.memberRepository.getMemberByEmail(body.getEmail()).ifPresent(member -> {
      throw new CustomException(ErrorMessage.EMAIL_DUPLICATED);
    });

    // 3. 기본 authroization 가져오기
    AuthorizationEntity authorization = this.authorizationRepository.getAuthorizationByLevel(
            MemberAuth.MEMBER.getValue())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));

    // 4. 멤버 저장
    MemberEntity memberToSave = MemberEntity.builder()
        .authorizationId(authorization.getAuthorizationId()).memberName(body.getMemberName())
        .sex(body.getSex()).birthday(body.getBirthday()).phone(body.getPhone())
        .address(body.getAddress()).email(body.getEmail()).password(body.getPassword()).build();
    MemberEntity member = this.memberRepository.save(memberToSave);

    // 5. access / refresh 토큰 만들기
    JwtDto.memberInfo memberInfo = new memberInfo();
    memberInfo.setMemberId(member.getMemberId());
    memberInfo.setMemberName(member.getMemberName());
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new MemberResDto.signUp(refreshToken, accessToken);
  }

  public MemberResDto.signIn signIn(MemberReqDto.signIn body) {
    // 1. 이메일 유저 여부 체크
    MemberEntity member = this.memberRepository.getMemberByEmail(body.getEmail())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_EMAIL_MEMBER_FOUND));

    // 2. 유저 비밀번호 체크
    if (!this.passwordEncoder.matches(body.getPassword(), member.getPassword())) {
      throw new CustomException(ErrorMessage.WRONG_PASSWORD);
    }

    // 3. access / refresh 토큰 만들기
    JwtDto.memberInfo memberInfo = new memberInfo();
    memberInfo.setMemberId(member.getMemberId());
    memberInfo.setMemberName(member.getMemberName());
    String refreshToken = this.jwtTokenProvider.createRefreshToken(memberInfo);
    String accessToken = this.jwtTokenProvider.createAccessToken(memberInfo);

    return new MemberResDto.signIn(refreshToken, accessToken);
  }

  public MemberResDto.checkChurchMember checkChurchMember(CustomUserDetails customUserDetails) {
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.getChurchMemberMapListByMemberId(
        customUserDetails.getMemberId());
    boolean isChurchMember = churchMemberMapList.size() > 0;

    return new MemberResDto.checkChurchMember(isChurchMember);
  }

  /**
   * POLICY: 부목사님은 해당 부서의 organList만 조회가능하지, 다른 부서는 볼 수 있어서는 안된다. 담임 목사님은 모든 부서를 조회할 수 있다.
   */
  public List<MemberResDto.getChurchWithBody> getMemberChurchesWithBodies(
      CustomUserDetails customUserDetails) {
    // 1. 유저 아이디로 교회 조회
    List<ChurchMemberMapEntity> churchMemberMapList = this.churchMemberMapRepository.getChurchMemberMapListByMemberId(
        customUserDetails.getMemberId());

    // 2. 유저가 다니는 교회별 부서 매핑
    List<ChurchEntity> churchList = new ArrayList<>();
    for (ChurchMemberMapEntity churchMemberMap : churchMemberMapList) {
      // 2-1. 유저가 다니는 교회 조회
      ChurchEntity church = this.churchRepository.getChurchByChurchId(churchMemberMap.getChurchId())
          .orElseThrow(() -> new CustomException(ErrorMessage.CHURCH_INVALIDATED));
      // 2-2. 교회별 부서 조회
      List<BodyEntity> bodyList = this.bodyRepository.getBodyListByChurchId(church.getChurchId());

      // 2-3. 최고 권한이면 모든 부서 리턴 (담임 목사)
      if (churchMemberMap.isLeader()) {
        church.setBodyList(bodyList);
        churchList.add(church);
        continue;
      }

      // 2-4. 교회별 부서 중, 유저가 속한 부서 필터링
      List<BodyEntity> validBodyList = new ArrayList<>();
      for (BodyEntity body : bodyList) {
        Optional<BodyMemberMapEntity> bodyMemberMap = this.bodyMemberMapRepository.geValidBodyMemberMapByBodyIdAndMemberId(
            body.getBodyId(), customUserDetails.getMemberId());
        // 2-5. 유저가 속한 부서만 add
        if (bodyMemberMap.isPresent()) {
          validBodyList.add(body);
        }
      }
      church.setBodyList(validBodyList);
      churchList.add(church);
    }

    // 3. 리턴
    return this.memberMapper.getMemberChurchesWithBodies(churchList);
  }

  public MemberResDto.getMemberInfo getMemberInfo(CustomUserDetails customUserDetails) {
    // 1. 유저 정보 검색
    MemberEntity member = this.memberRepository.getMemberByMemberId(customUserDetails.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));

    // 2. 리턴
    return this.memberMapper.getMemberInfo(member);
  }

  /**
   * POLICY: 셀 조회 권한
   *
   * @churchLeader --> 모든 부서 밑의 모든 셀이 보여야 함 (담임 목사님)
   * @bodyLeader --> 해당 부서 밑의 모든 셀이 보여야 함 (부서 목사님)
   * @organLeader --> 해당 올건 셀 전체 + 본인이 속한 셀이 보여야 함 (팀장)
   * @cellLeader & member --> 해당 셀만 보여야 함 (셀장 & 셀원)
   * <p>
   * 상위부터 권한을 조회하는 이유는, 상위에 계신 목사님도 하위 셀에 속할 수 있기 때문이에요.
   */
  public List<MemberResDto.getCellByBodyIdAndMemberId> getCellListByBodyIdAndMemberId(
      CustomUserDetails customUserDetails, Long bodyId) {

    // 1. 멤버가 속한 교회가져오기
    Long memberId = customUserDetails.getMemberId();
    BodyEntity body = this.bodyRepository.getBodyByBodyId(bodyId)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_BODY));
    ChurchEntity church = this.churchRepository.getChurchByChurchId(body.getChurchId())
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_CHURCH));

    // 2. churchLeader OR bodyLeader OR organLeader OR cellLeader & member 검사
    List<CellEntity> cellList;
    ChurchMemberMapEntity churchMemberMap = this.churchMemberMapRepository.getValidChurchMemberMapByChurchIdAndMemberId(
            church.getChurchId(), memberId)
        .orElseThrow(() -> new CustomException(ErrorMessage.NOT_CHURCH_MEMBER));
    boolean isChurchLeader = churchMemberMap.isLeader();
    BodyMemberMapEntity bodyMemberMap = this.bodyMemberMapRepository.geValidBodyMemberMapByBodyIdAndMemberId(
        bodyId, memberId).orElseThrow(() -> new CustomException(ErrorMessage.NO_USER_UNDER_BODY));
    boolean isBodyLeader = bodyMemberMap.isLeader();

    // 3.1 담임목사님이거나 부목사님일 경우
    if (isChurchLeader | isBodyLeader) {
      cellList = this.getCellListForChurchLeaderAndBodyLeader(bodyId);
    }
    // 3.2 올건리더 혹은 셀리더 혹은 셀원일 경우
    else {
      cellList = this.getCellListForOrganLeaderAndCellLeaderAndMember(memberId, bodyId);
    }

    // 5. DTO 매핑
    return this.memberMapper.getCellListByBodyIdAndMemberId(cellList);
  }

  private List<CellEntity> getCellListForChurchLeaderAndBodyLeader(Long bodyId) {
    // 2. 바디 밑에 속한 올건 리스트 조회
    List<OrganEntity> organListByBodyId = this.organRepository.getOrganListByBodyId(bodyId);
    List<Long> organIdListByBodyId = organListByBodyId.stream().map(OrganEntity::getBodyId)
        .toList();

    // 3. 올건 밑에 속한 셀 리스트 조회
    return this.cellRepository.getCellListByOrganIdList(organIdListByBodyId);
  }

  private List<CellEntity> getCellListForOrganLeaderAndCellLeaderAndMember(Long memberId,
      Long bodyId) {
    // 1. 바디 밑에 속한 올건 리스트 조회
    List<OrganEntity> organListByBodyId = this.organRepository.getOrganListByBodyId(bodyId);
    List<Long> organIdListByBodyId = organListByBodyId.stream().map(OrganEntity::getOrganId)
        .toList();

    // 2. 유저가 속한 올건 조회 및 필터링
    List<OrganMemberMapEntity> organMemberMapList = this.organMemberMapRepository.getOrganMemberMapListByOrganIdListAndMemberId(
        organIdListByBodyId, memberId);
    List<Long> organLeaderIdListByMemberId = new ArrayList<>();
    List<Long> organIdListByMemberId = new ArrayList<>();
    organMemberMapList.forEach(organMemberMap -> {
      if (organMemberMap.isLeader()) {
        organLeaderIdListByMemberId.add(organMemberMap.getOrganId());
      } else {
        organIdListByMemberId.add(organMemberMap.getOrganId());
      }
    });

    // 3. 유저가 속한 올건 중 셀 리스트 조회
    List<CellEntity> cellListByBodyId = this.cellRepository.getCellListByOrganIdList(
        organIdListByMemberId);
    List<Long> cellIdListByBodyId = cellListByBodyId.stream().map(CellEntity::getCellId).toList();

    // 4. 유저가 속한 셀 리스트 조회
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellIdListAndMemberId(
        cellIdListByBodyId, memberId);
    List<Long> cellIdListByMemberId = cellMemberMapList.stream().map(CellMemberMapEntity::getCellId)
        .toList();

    // 5. 유저가 속한 셀 리스트 필터링
    List<CellEntity> cellListByMemberId = cellListByBodyId.stream()
        .filter(cell -> cellIdListByMemberId.contains(cell.getCellId())).toList();

    // 6. 셀 인원 조회 및 매핑
    List<CellEntity> cellList = new ArrayList<>();
    List<CellEntity> memberCellList = cellListByMemberId.stream().map(cell -> {
      List<CellMemberMapEntity> cellMemberMapListByCellId = this.cellMemberMapRepository.getCellMemberMapListByCellId(
          cell.getCellId());
      cell.setCellMemberCount(cellMemberMapListByCellId.size());
      return cell;
    }).toList();

    // 7. 올건리더로서 올건이 존재할 경우, 올건 밑에 속한 셀 리스트 조회 및 추가
    if (organLeaderIdListByMemberId.size() > 0) {
      List<CellEntity> cellListAsOrganLeader = this.cellRepository.getCellListByOrganIdList(
          organLeaderIdListByMemberId);
      cellList.addAll(cellListAsOrganLeader);
    }

    cellList.addAll(memberCellList);

    return cellList;
  }

  public void askToJoinChurchAndBody(Long churchId, Long bodyId,
      CustomUserDetails customUserDetails) {
    // 1. 교회 및 부서 존재 여부 체크
    this.churchRepository.getChurchByChurchId(churchId)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_CHURCH));
    this.bodyRepository.getBodyByBodyId(bodyId)
        .orElseThrow(() -> new CustomException(ErrorMessage.NO_BODY));

    // 2. 이미 가입된 교회인지 체크
    this.churchMemberMapRepository.getValidChurchMemberMapByChurchIdAndMemberId(churchId,
        customUserDetails.getMemberId()).ifPresent(churchMemberMap -> {
      throw new CustomException(ErrorMessage.ALREADY_JOINED_CHURCH);
    });
    this.churchMemberMapRepository.getInvalidChurchMemberMapByChurchIdAndMemberId(churchId,
        customUserDetails.getMemberId()).ifPresent(churchMemberMap -> {
      throw new CustomException(ErrorMessage.ALREADY_ASKED_TO_JOIN_CHURCH);
    });

    // 3. 이미 가입된 부서인지 체크
    this.bodyMemberMapRepository.geValidBodyMemberMapByBodyIdAndMemberId(bodyId,
        customUserDetails.getMemberId()).ifPresent(bodyMemberMap -> {
      throw new CustomException(ErrorMessage.ALREADY_JOINED_BODY);
    });
    this.bodyMemberMapRepository.geInvalidBodyMemberMapByBodyIdAndMemberId(bodyId,
        customUserDetails.getMemberId()).ifPresent(bodyMemberMap -> {
      throw new CustomException(ErrorMessage.ALREADY_ASKED_TO_JOIN_BODY);
    });

    // 4. 교회 및 부서에 가입 요청
    ChurchMemberMapEntity churchMemberMap = ChurchMemberMapEntity.builder().churchId(churchId)
        .memberId(customUserDetails.getMemberId()).isValid(false).build();
    this.churchMemberMapRepository.save(churchMemberMap);

    BodyMemberMapEntity bodyMemberMap = BodyMemberMapEntity.builder().bodyId(bodyId)
        .memberId(customUserDetails.getMemberId()).isValid(false).build();
    this.bodyMemberMapRepository.save(bodyMemberMap);
  }

  public MemberResDto.findMemberEmail findMemberEmail(MemberReqDto.findMemberEmail query) {
    // 1. Data-destructuring
    String memberName = query.getMemberName();
    LocalDate birthday = query.getBirthday();

    // 2. 이메일 여부 조회
    MemberEntity member = this.memberRepository.getMemberByMemberNameAndBirthday(memberName,
        birthday).orElseThrow(() -> new CustomException(ErrorMessage.CAN_NOT_FIND_EMAIL));

    return new MemberResDto.findMemberEmail(member.getEmail());
  }

  @Transactional
  public void requestTemporaryPassword(MemberReqDto.requestTemporaryPassword body) {
    // 1. Data-destructuring
    String email = body.getEmail();
    String memberName = body.getMemberName();

    // 2. 멤버 찾기
    MemberEntity member = this.memberRepository.getMemberByEmailAndMemberName(email, memberName)
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_EMAIL_NOT_MATCHED));

    // 3. 임시 비밀번호 생성 및 비밀번호 업데이트
    String temporaryPassword = Util.getRandomString(10);
    this.memberRepository.updatePasswordByMemberId(member.getMemberId(), temporaryPassword);

    // TODO 4. 이메일로 임시 비밀번호 전송
    throw new CustomException(ErrorMessage.MEMBER_NOT_FOUND);
  }

  public void changePassword(MemberReqDto.changePassword body) {
    // 1. Data-destructuring
    String email = body.getEmail();
    String currentPassword = body.getCurrentPassword();
    String newPassword = body.getNewPassword();
    String newConfirmPassword = body.getNewConfirmPassword();

    // 2. 새로운 비밀번호와 새로운 확인 비밀번호가 같은지 확인
    if (!newPassword.equals(newConfirmPassword)) {
      throw new CustomException(ErrorMessage.NEW_PASSWORDS_NOT_EQUAL);
    }

    // 2. 현재 비밀번호 확인
    MemberEntity member = this.memberRepository.getMemberByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
    if (!this.passwordEncoder.matches(currentPassword, member.getPassword())) {
      throw new CustomException(ErrorMessage.WRONG_PASSWORD);
    }

    // 3. 새로운 비밀번호 셋팅
    String newPasswordHashed = this.passwordEncoder.encode(newPassword);
    this.memberRepository.updatePasswordByMemberId(member.getMemberId(), newPasswordHashed);
  }

  public void deactivate(CustomUserDetails customUserDetails) {
    Long memberId = customUserDetails.getMemberId();

    this.memberRepository.deactivate(memberId);
  }
}
