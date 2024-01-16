package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.domain.auth.app.repository.AuthorizationRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellMapper;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellMemberMapRepository;
import feedmysheep.feedmysheepapi.domain.cell.app.repository.CellRepository;
import feedmysheep.feedmysheepapi.domain.member.app.repository.MemberRepository;
import feedmysheep.feedmysheepapi.global.interceptor.auth.MemberAuth;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import feedmysheep.feedmysheepapi.global.utils.response.error.CustomException;
import feedmysheep.feedmysheepapi.global.utils.response.error.ErrorMessage;
import feedmysheep.feedmysheepapi.models.AuthorizationEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.ChurchMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellService {

  private final CellRepository cellRepository;
  private final CellMemberMapRepository cellMemberMapRepository;
  private final MemberRepository memberRepository;
  private final AuthorizationRepository authorizationRepository;
  private final CellMapper cellMapper;
  private final CellProcessor cellProcessor;

  @Autowired
  public CellService(CellRepository cellRepository, CellMemberMapRepository cellMemberMapRepository,
      MemberRepository memberRepository, AuthorizationRepository authorizationRepository,
      CellProcessor cellProcessor, CellMapper cellMapper) {
    this.cellRepository = cellRepository;
    this.cellMemberMapRepository = cellMemberMapRepository;
    this.memberRepository = memberRepository;
    this.authorizationRepository = authorizationRepository;
    this.cellMapper = cellMapper;
    this.cellProcessor = cellProcessor;
  }

  public List<CellResDto.getCellMemberByCellId> getCellMemberListByCellId(Long cellId,
      CustomUserDetails customUserDetails) {
    Long memberId = customUserDetails.getMemberId();

    // 1. 본인이 속한 셀인지 확인
    List<CellMemberMapEntity> cellMemberMapList = this.cellMemberMapRepository.getCellMemberMapListByCellId(
        cellId);
    List<Long> memberIdList = cellMemberMapList.stream().map(CellMemberMapEntity::getMemberId)
        .toList();
    boolean isCellMember = cellMemberMapList.stream()
        .anyMatch(cellMemberMapEntity -> cellMemberMapEntity.getMemberId().equals(memberId));
    // 1.1 본인이 속한 셀이 아닌데, 권한이 organLeader 이상인 경우에만 pass
    if (!isCellMember) {
      MemberEntity member = this.memberRepository.getMemberByMemberId(memberId)
          .orElseThrow(() -> new CustomException(ErrorMessage.MEMBER_NOT_FOUND));
      AuthorizationEntity authorization = this.authorizationRepository.getAuthorizationByAuthorizationId(
              member.getAuthorizationId())
          .orElseThrow(() -> new CustomException(ErrorMessage.NO_AUTHORIZATION));
      if (authorization.getLevel() < MemberAuth.ORGAN_LEADER.getValue()) {
        throw new CustomException(ErrorMessage.NOT_AUTHORIZED);
      }
    }

    // 2. 셀멤버맵으로 멤버리스트 조회
    List<MemberEntity> memberList = this.memberRepository.getMemberListByMemberIdList(memberIdList);

    // 3. 멤버에 isLeader 추가
    List<MemberEntity> memberListWithIsLeader = this.cellProcessor.addIsLeaderToMemberList(memberList, cellMemberMapList);

    // 4. 멤버에 isBirthdayThisMonth 추가
    List<MemberEntity> memberListWithIsBirthdayThisMonth = this.cellProcessor.addIsBirthdayThisMonthToMemberList(memberListWithIsLeader);

    // 4. 정렬 - 셀리더가 맨 앞으로 && 생일자가 그 뒤로 && 이름으로 정렬
    List<MemberEntity> sortedMemberList = this.cellProcessor.sortCellMemberList(memberListWithIsBirthdayThisMonth);

    return this.cellMapper.getCellMemberListByCellId(sortedMemberList);
  }
}
