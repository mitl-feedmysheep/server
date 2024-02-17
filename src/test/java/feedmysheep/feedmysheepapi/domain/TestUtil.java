package feedmysheep.feedmysheepapi.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtil {

  private static final Random RANDOM = new Random();
  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String NUMBERS = "0123456789";

  public static String getRandomPhone() {
    int secondSegment = RANDOM.nextInt(10000); // 0~9999 사이의 숫자 생성
    int thirdSegment = RANDOM.nextInt(10000); // 0~9999 사이의 숫자 생성
    return String.format("010-%04d-%04d", secondSegment, thirdSegment);
  }

  public static String getRandomString() {
    return getRandomString(10); // 기본값으로 10 사용
  }

  public static String getRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int randomIndex = RANDOM.nextInt(ALPHABET.length());
      stringBuilder.append(ALPHABET.charAt(randomIndex));
    }
    return stringBuilder.toString();
  }

  public static int getRandomNum(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int randomIndex = RANDOM.nextInt(NUMBERS.length());
      stringBuilder.append(NUMBERS.charAt(randomIndex));
    }
    String randomString = stringBuilder.toString();
    return Integer.parseInt(randomString);
  }

  public static String getRandomEmail() {
    return getRandomString(10) + "@" + getRandomString(10) + ".com";
  }

  public static String getRandomSex() {
    return Math.random() < 0.5 ? "M" : "F";
  }

  public static LocalDate getRandomBirthday() {
    int minAge = 0; // 최소 연령 설정 (예: 18세)
    int maxAge = 100; // 최대 연령 설정 (예: 65세)

    LocalDate currentDate = LocalDate.now();
    LocalDate minValidDate = currentDate.minusYears(maxAge);
    LocalDate maxValidDate = currentDate.minusYears(minAge);

    long minDay = minValidDate.toEpochDay();
    long maxDay = maxValidDate.toEpochDay();
    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

    return LocalDate.ofEpochDay(randomDay);
  }

  public static UUID getRandomUUID() {
    return UuidCreator.getTimeOrdered();
  }
}
