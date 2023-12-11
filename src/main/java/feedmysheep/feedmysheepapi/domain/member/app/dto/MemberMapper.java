package feedmysheep.feedmysheepapi.domain.member.app.dto;

import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.ChurchEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  List<MemberResDto.getChurchWithBody> getMemberChurchesWithBodies(List<ChurchEntity> churchList);

  MemberResDto.getMemberInfo getMemberInfo(MemberEntity member);

  List<MemberResDto.getCellByBodyIdAndMemberId> getCellListByBodyIdAndMemberId(List<CellEntity> cellList);
}
