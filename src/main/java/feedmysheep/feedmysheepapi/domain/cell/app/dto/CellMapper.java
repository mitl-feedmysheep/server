package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CellMapper {

  List<CellResDto.getCellMemberByCellId> getCellMemberListByCellId(List<MemberEntity> memberList);

  List<CellResDto.getCellGathering> getCellGatheringListByCellId(
      List<CellGatheringEntity> cellGatheringList);

  List<CellResDto.createCell> getCellListByCellId(List<CellEntity> cellIdList);
}
