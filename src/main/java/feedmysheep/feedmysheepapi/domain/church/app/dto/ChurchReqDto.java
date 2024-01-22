package feedmysheep.feedmysheepapi.domain.church.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Data
public class ChurchReqDto {

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
}