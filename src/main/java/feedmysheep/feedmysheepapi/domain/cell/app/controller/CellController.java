package feedmysheep.feedmysheepapi.domain.cell.app.controller;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCellMemberByCellId;
import feedmysheep.feedmysheepapi.domain.cell.app.service.CellService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/cell")
public class CellController {

  private final CellService cellService;

  /**
   * POLICY: 해당 cellId를 가질 수 있다는 뜻은, 셀이 노출된 경우이기 때문에 일반적인 abusing은 없을 것이라고 판단 하지만, 권한없는 누군가가 API를
   * ID별로 무한대로 호출한다면, 노출이 된다. 그래도, 셀리스트가 노출된 OrganLeader까지는 막지말자. 셀리더 이하부터는 본인이 속한 곳이 아니면 조회를 하지 못하게
   * 막는다.
   */
  // TODO: 케이스별 테스트도 필요함 && 케이스별 테스트코드 필요
  @GetMapping("/{cellId}/members")
  public List<getCellMemberByCellId> getCellMemberListByCellId(@PathVariable UUID cellId) {
    return this.cellService.getCellMemberListByCellId(cellId);
  }

  @GetMapping("/{cellId}/gatherings-and-prayers-count")
  public CellResDto.getGatheringsAndPrayersCount getGatheringsAndPrayersCountByCellId(
      @PathVariable UUID cellId) {
    return this.cellService.getGatheringsAndPrayersCountByCellId(cellId);
  }

  @GetMapping("/{cellId}/cell-gatherings")
  public List<CellResDto.getCellGathering> getCellGatheringListByCellId(@PathVariable UUID cellId,
      CellReqDto.getCellGatheringListByCellId query) {
    return this.cellService.getCellGatheringListByCellId(cellId, query);
  }

  @GetMapping("/cell-gathering/{cellGatheringId}")
  public CellResDto.getCellGatheringAndMemberListAndPrayerList getCellGatheringAndMemberListAndPrayerList(
      @PathVariable UUID cellGatheringId) {
    return this.cellService.getCellGatheringAndMemberListAndPrayerList(cellGatheringId);
  }

  @PostMapping("/cell-gathering/cell-gathering-member/{cellGatheringMemberId}/cell-gathering-member-prayer")
  public void insertCellGatheringMemberPrayerListByCellGatheringMemberId(
      @PathVariable UUID cellGatheringMemberId,
      @Valid @RequestBody List<String> prayerRequestList) {
    this.cellService.insertCellGatheringMemberPrayerListByCellGatheringMemberId(
        cellGatheringMemberId, prayerRequestList);
  }

  @PutMapping("/cell-gathering/cell-gathering-member/{cellGatheringMemberId}")
  public void updateCellGatheringMemberByCellGatheringMemberId(
      @PathVariable UUID cellGatheringMemberId,
      @Valid @RequestBody CellReqDto.updateCellGatheringMemberByCellGatheringMemberId body) {
    this.cellService.updateCellGatheringMemberByCellGatheringMemberId(cellGatheringMemberId, body);
  }

  @GetMapping("/cell-gathering/cell-gathering-member/{cellGatheringMemberId}/cell-gathering-member-prayer")
  public List<CellResDto.cellGatheringMemberPrayer> getCellGatheringMemberPrayerListByCellGatheringMemberId(
      @PathVariable UUID cellGatheringMemberId) {
    return this.cellService.getCellGatheringMemberPrayerListByCellGatheringMemberId(
        cellGatheringMemberId);
  }

  @PutMapping("/cell-gathering/cell-gathering-member/cell-gathering-member-prayer")
  public void updateCellGatheringMemberPrayerList(
      @Valid @RequestBody List<CellReqDto.updateCellGatheringMemberPrayer> body,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    this.cellService.updateCellGatheringMemberPrayerList(body, customUserDetails);
  }

  @DeleteMapping("/cell-gathering/cell-gathering-member/cell-gathering-member-prayer")
  public void deleteCellGatheringMemberPrayerList(
      @Valid @RequestBody CellReqDto.deleteCellGatheringMemberPrayer body,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    this.cellService.deleteCellGatheringMemberPrayerList(body, customUserDetails);
  }

  @GetMapping("/{cellId}/info")
  public CellResDto.getCellByCellId getCellByCellId(@PathVariable UUID cellId) {
    return this.cellService.getCellByCellId(cellId);
  }

  @DeleteMapping("/cell-gathering/{cellGatheringId}")
  public void deleteCellGatheringByCellGatheringId(@PathVariable UUID cellGatheringId) {
    this.cellService.deleteCellGatheringByCellGatheringId(cellGatheringId);
  }

  @PostMapping("/{cellId}/cell-gathering")
  public CellResDto.createCellGatheringByCellId createCellGatheringByCellId(
      @PathVariable UUID cellId, @Valid @RequestBody CellReqDto.createCellGatheringByCellId body,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return this.cellService.createCellGatheringByCellId(cellId, body, customUserDetails);
  }

  @PutMapping("/cell-gathering/{cellGatheringId}")
  public void updateCellGatheringByCellGatheringId(@PathVariable UUID cellGatheringId,
      @Valid @RequestBody CellReqDto.updateCellGatheringByCellGatheringId body) {
    this.cellService.updateCellGatheringByCellGatheringId(cellGatheringId, body);
  }
}
