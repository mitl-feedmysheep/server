package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCellMemberByCellId;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import java.util.List;
import java.util.UUID;

public interface CellService {

  List<getCellMemberByCellId> getCellMemberListByCellId(UUID cellId);

  CellResDto.getGatheringsAndPrayersCount getGatheringsAndPrayersCountByCellId(UUID cellId);

  List<CellResDto.getCellGathering> getCellGatheringListByCellId(UUID cellId,
      CellReqDto.getCellGatheringListByCellId query);

  CellResDto.getCellGatheringAndMemberListAndPrayerList getCellGatheringAndMemberListAndPrayerList(
      UUID cellGatheringId);

  List<CellResDto.cellGatheringMemberPrayer> getCellGatheringMemberPrayerListByCellGatheringMemberId(
      UUID cellGatheringMemberId);

  void updateCellGatheringMemberByCellGatheringMemberId(UUID cellGatheringMemberId,
      CellReqDto.updateCellGatheringMemberByCellGatheringMemberId body);

  void insertCellGatheringMemberPrayerListByCellGatheringMemberId(UUID cellGatheringMemberId,
      List<String> prayerRequestList);

  void updateCellGatheringMemberPrayerList(
      List<CellReqDto.updateCellGatheringMemberPrayer> cellGatheringMemberPrayerList,
      CustomUserDetails customUserDetails);

  void deleteCellGatheringMemberPrayerList(CellReqDto.deleteCellGatheringMemberPrayer body,
      CustomUserDetails customUserDetails);

  CellResDto.getCellByCellId getCellByCellId(UUID cellId);

  void deleteCellGatheringByCellGatheringId(UUID cellGatheringId);

  CellResDto.createCellGatheringByCellId createCellGatheringByCellId(UUID cellId,
      CellReqDto.createCellGatheringByCellId body, CustomUserDetails customUserDetails);

  void updateCellGatheringByCellGatheringId(UUID cellGatheringId,
      CellReqDto.updateCellGatheringByCellGatheringId body);
}