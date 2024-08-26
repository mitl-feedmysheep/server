package feedmysheep.feedmysheepapi.global.utils;

import java.util.Random;

public class Util {
  private static final Random RANDOM = new Random();
  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static String getRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int randomIndex = RANDOM.nextInt(ALPHABET.length());
      stringBuilder.append(ALPHABET.charAt(randomIndex));
    }
    return stringBuilder.toString();
  }
}
