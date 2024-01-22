package feedmysheep.feedmysheepapi.domain.cell.app.controller;

import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellReqDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto.getCellMemberByCellId;
import feedmysheep.feedmysheepapi.domain.cell.app.service.CellService;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/cell")
public class CellController {

  private final CellService cellService;

  @Autowired
  public CellController(CellService cellService) {
    this.cellService = cellService;
  }

  /**
   * POLICY: 해당 cellId를 가질 수 있다는 뜻은, 셀이 노출된 경우이기 때문에 일반적인 abusing은 없을 것이라고 판단
   * 하지만, 권한없는 누군가가 API를 ID별로 무한대로 호출한다면, 노출이 된다.
   * 그래도, 셀리스트가 노출된 OrganLeader까지는 막지말자. 셀리더 이하부터는 본인이 속한 곳이 아니면 조회를 하지 못하게 막는다.
   */
  // TODO: 케이스별 테스트도 필요함 && 케이스별 테스트코드 필요
  @GetMapping("/{cellId}/members")
  public List<getCellMemberByCellId> getCellMemberListByCellId(@PathVariable Long cellId,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return this.cellService.getCellMemberListByCellId(cellId, customUserDetails);
  }

  @GetMapping("/{cellId}/gatherings-and-prayers-count")
  public CellResDto.getGatheringsAndPrayersCount getGatheringsAndPrayersCountByCellId(
      @PathVariable Long cellId) {
    return this.cellService.getGatheringsAndPrayersCountByCellId(cellId);
  }

  @GetMapping("/{cellId}/cell-gatherings")
  public List<CellResDto.getCellGathering> getCellGatheringListByCellId(@PathVariable Long cellId, CellReqDto.getCellGatheringListByCellId query) {
    return this.cellService.getCellGatheringListByCellId(cellId, query);
  }
}
