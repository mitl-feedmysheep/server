package feedmysheep.feedmysheepapi.domain.church.app.service;

import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchReqDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto;
import feedmysheep.feedmysheepapi.domain.church.app.dto.ChurchResDto.getChurch;
import feedmysheep.feedmysheepapi.global.utils.jwt.CustomUserDetails;
import java.util.List;
import java.util.UUID;

public interface ChurchService {

  List<getChurch> getChurchList(CustomUserDetails customUserDetails, String churchName);

  List<ChurchResDto.getBodyByChurchId> getBodyListByChurchId(CustomUserDetails customUserDetails,
      UUID churchId);

  void register(ChurchReqDto.register body, CustomUserDetails customUserDetails);

  ChurchResDto.getMemberEventListByMemberId getMemberEventsByBodyId(
      ChurchReqDto.getMemberEventsByBodyId query, UUID bodyId);
}