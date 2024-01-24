package feedmysheep.feedmysheepapi.domain.cell.app.controller;

import feedmysheep.feedmysheepapi.domain.cell.app.cellService.CellService;
import feedmysheep.feedmysheepapi.domain.cell.app.dto.CellResDto;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app")
public class CellController {

  private final CellService cellService;

  @Autowired
  public CellController(CellService cellService) {
    this.cellService = cellService;
  }

  @GetMapping("/cell/{cellId}/info")
  public List<CellResDto.getCell> getCellList(
      @PathVariable Long cellId, @AuthenticationPrincipal CustomUserDetails customUserDetails
  ) {
    return this.cellService.getCellList(cellId, customUserDetails);

  }
}
