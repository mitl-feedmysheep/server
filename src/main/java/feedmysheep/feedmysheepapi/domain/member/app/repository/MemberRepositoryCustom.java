package feedmysheep.feedmysheepapi.domain.member.app.repository;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import java.util.UUID;

public interface MemberRepositoryCustom {

  List<MemberEntity> findAllByMemberIdListAndMonth(List<UUID> memberIdList, Integer month,
      Integer offset, Integer limit);

  // TODO 테스트코드
  List<MemberEntity> findAllByMemberIdList(List<UUID> memberIdList);

  // TODO 테스트코드
  void updatePasswordByMemberId(UUID memberId, String newPassword);

  // TODO 테스트코드
  void deactivate(UUID memberId);
}
