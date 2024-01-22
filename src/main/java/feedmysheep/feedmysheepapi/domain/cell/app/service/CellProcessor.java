package feedmysheep.feedmysheepapi.domain.cell.app.service;

import feedmysheep.feedmysheepapi.models.CellGatheringEntity;
import feedmysheep.feedmysheepapi.models.CellGatheringMemberEntity;
import feedmysheep.feedmysheepapi.models.CellMemberMapEntity;
import feedmysheep.feedmysheepapi.models.MemberEntity;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CellProcessor {

  public List<MemberEntity> addIsLeaderToMemberList(List<MemberEntity> cellMemberList,
      List<CellMemberMapEntity> cellMemberMapList) {
    // { memberId: boolean... } 멤버아이디: 리더여부
    Map<Long, Boolean> memberIdLeaderMap = cellMemberMapList.stream()
        .collect(Collectors.toMap(CellMemberMapEntity::getMemberId, CellMemberMapEntity::isLeader));

    // cellMemberList를 순회하면서 memberIdLeaderMap을 참조하여 리더 여부를 설정
    cellMemberList.forEach(member -> {
      Boolean isLeader = memberIdLeaderMap.get(member.getMemberId());
      member.setLeader(isLeader);
    });

    return cellMemberList;
  }

  public List<MemberEntity> addIsBirthdayThisMonthToMemberList(List<MemberEntity> cellMemberList) {
    // 이번달 생일자인지 여부를 추가
    cellMemberList.forEach(member -> {
      // 현재 날짜를 가져와서 월을 비교
      LocalDate currentDate = LocalDate.now();
      member.setBirthdayThisMonth(member.getBirthday().getMonth() == currentDate.getMonth());
    });

    return cellMemberList;
  }

  public List<MemberEntity> sortCellMemberList(List<MemberEntity> cellMemberList) {
    // 정렬 조건에 따라 Comparator를 체이닝하여 생성
    Comparator<MemberEntity> memberComparator = Comparator.comparing(MemberEntity::isLeader,
            Comparator.reverseOrder()) // 셀리더가 맨 앞으로
        .thenComparing(MemberEntity::isBirthdayThisMonth, Comparator.reverseOrder()) // 생일자가 그 뒤로
        .thenComparing(MemberEntity::getMemberName); // 가나다순으로 정렬

    // 리스트 정렬
    cellMemberList.sort(memberComparator);

    return cellMemberList;
  }

  public List<CellGatheringEntity> addNumberAndDayToCellGatheringList(
      List<CellGatheringEntity> cellGatheringList) {
    // 1. cellGatheringList를 날짜순으로 정렬
    List<CellGatheringEntity> sortedCellGatheringListList = new ArrayList<>(
        cellGatheringList.stream()
            .sorted(Comparator.comparing(CellGatheringEntity::getGatheringDate)).toList());

    // 2. sortedCellGatheringListList를 순회하면서 번호와 요일을 추가
    sortedCellGatheringListList.forEach(cellGathering -> {
      // 2.1 순서 설정
      int number = cellGatheringList.indexOf(cellGathering) + 1;
      cellGathering.setNumberOfGathering(number);

      // 2.2 요일 설정
      LocalDate date = cellGathering.getGatheringDate();
      cellGathering.setDayOfWeek(
          date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
    });

    // 3. 역정렬
    Collections.reverse(sortedCellGatheringListList);

    return sortedCellGatheringListList;
  }

  public List<CellGatheringEntity> filterCellGatheringListByMonth(
      List<CellGatheringEntity> cellGatheringList, int month) {
    return cellGatheringList.stream()
        .filter(cellGathering -> cellGathering.getGatheringDate().getMonth().getValue() == month)
        .toList();
  }

  public CellGatheringEntity addAttendanceCountToCellGathering(CellGatheringEntity cellGathering,
      List<CellGatheringMemberEntity> cellGatheringMemberList) {
    int totalWorhsipAttendanceCount = (int) cellGatheringMemberList.stream()
        .filter(CellGatheringMemberEntity::isWorshipAttendance).count();
    int totalCellGatheringAttendanceCount = (int) cellGatheringMemberList.stream()
        .filter(CellGatheringMemberEntity::isCellGatheringAttendance).count();

    cellGathering.setTotalWorshipAttendanceCount(totalWorhsipAttendanceCount);
    cellGathering.setTotalCellGatheringAttendanceCount(totalCellGatheringAttendanceCount);

    return cellGathering;
  }


}
