package feedmysheep.feedmysheepapi.domain.church.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class ChurchReqDto {

//  @AllArgsConstructor
//  @Getter
//  public static class mainAndSubScreen {
//    @NotEmpty(message = "메인 스크린 값은 존재해야해요.")
//    private String mainScreen;
//    @Nullable
//    private String subScreen;
//  }
@Getter
@RequiredArgsConstructor
public static class register {
    // 필수값
    @NotNull
    private String churchName;
    @NotNull
    private String churchLocation;

    //필수값 x
    private String churchLogoUrl;
    private String churchNumber;
    private String homepageUrl;
    private String churchDescription;
}
}