package feedmysheep.feedmysheepapi.domain.cell.app.dto;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCell;
import feedmysheep.feedmysheepapi.models.CellEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberPrayerEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CellMapper {

  List<CellResDto.getCellMemberByCellId> getCellMemberListByCellId(List<MemberEntity> memberList);

  List<CellResDto.getCellGathering> getCellGatheringListByCellId(
      List<CellGatheringEntity> cellGatheringList);

  CellResDto.getCellGatheringAndMemberListAndPrayerList setCellGathering(
      CellGatheringEntity cellGatheringEntity);

  CellServiceDto.cellGatheringMember setCellGatheringMember(
      CellGatheringMemberEntity cellGatheringMember);

  List<CellServiceDto.cellGatheringMemberPrayer> setCellGatheringMemberPrayerList(
      List<CellGatheringMemberPrayerEntity> cellGatheringMemberPrayerList);

  List<getCell> getCellList(List<CellEntity> cellList);


}
