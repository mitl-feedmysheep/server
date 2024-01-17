package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CellMapper {

  List<CellResDto.getCellMemberByCellId> getCellMemberListByCellId(List<MemberEntity> memberList);

  CellResDto.getGatheringsAndPrayersCount getGatheringsAndPrayersCount(int totalGatheringCount,
      int totalPrayerRequestCount);
}
