package feedmysheep.feedmysheepapi.domain;

import java.util.Random;

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
}
