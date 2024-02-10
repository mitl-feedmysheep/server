package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCellMemberByCellId;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import java.util.List;

public interface CellService {

  List<getCellMemberByCellId> getCellMemberListByCellId(Long cellId,
      CustomUserDetails customUserDetails);

  CellResDto.getGatheringsAndPrayersCount getGatheringsAndPrayersCountByCellId(Long cellId);

  List<CellResDto.getCellGathering> getCellGatheringListByCellId(Long cellId,
      CellReqDto.getCellGatheringListByCellId query);

  CellResDto.getCellGatheringAndMemberListAndPrayerList getCellGatheringAndMemberListAndPrayerList(
      Long cellGatheringId);

  List<CellResDto.cellGatheringMemberPrayer> getCellGatheringMemberPrayerListByCellGatheringMemberId(
      Long cellGatheringMemberId);

  void updateCellGatheringMemberByCellGatheringMemberId(Long cellGatheringMemberId,
      CellReqDto.updateCellGatheringMemberByCellGatheringMemberId body,
      CustomUserDetails customUserDetails);

  void insertCellGatheringMemberPrayerListByCellGatheringMemberId(Long cellGatheringMemberId,
      List<String> prayerRequestList, CustomUserDetails customUserDetails);

  void updateCellGatheringMemberPrayerList(
      List<CellReqDto.updateCellGatheringMemberPrayer> cellGatheringMemberPrayerList,
      CustomUserDetails customUserDetails);

  void deleteCellGatheringMemberPrayerList(CellReqDto.deleteCellGatheringMemberPrayer body,
      CustomUserDetails customUserDetails);

  CellResDto.getCellByCellId getCellByCellId(Long cellId);

  void deleteCellGatheringByCellGatheringId(CustomUserDetails customUserDetails,
      Long cellGatheringId);

  CellResDto.createCellGatheringByCellId createCellGatheringByCellId(Long cellId,
      CellReqDto.createCellGatheringByCellId body, CustomUserDetails customUserDetails);

  void updateCellGatheringByCellGatheringId(Long cellGatheringId,
      CellReqDto.updateCellGatheringByCellGatheringId body, CustomUserDetails customUserDetails);
}