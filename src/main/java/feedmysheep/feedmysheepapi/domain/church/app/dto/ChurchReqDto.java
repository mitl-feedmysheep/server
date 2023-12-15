package feedmysheep.feedmysheepapi.domain.church.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Data
public class ChurchReqDto {

  @AllArgsConstructor
  @Getter
  public static class mainAndSubScreen {

    @NotEmpty(message = "메인 스크린 값은 존재해야해요.")
    private String mainScreen;
    @Nullable
    private String subScreen;
  }

  @RequiredArgsConstructor
  @Getter
  public static class register {

    @NotBlank
    private String churchName;
    @NotBlank
    private String churchLocation;

    @Nullable
    private String churchLogoUrl;
    @Nullable
    private String churchNumber;
    @Nullable
    private String homepageUrl;
    @Nullable
    private String churchDescription;
  }

  @RequiredArgsConstructor
  @Getter
  public static class getMemberEventsByBodyId {

    public LocalDate birthday;

  }
}