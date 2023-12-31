package feedmysheep.feedmysheepapi.global.policy;

public class CONSTANT {

  public static class JWT {

    public final static String SECRET_KEY = "RejoiceAlwaysPrayContinuallyGiveThanksInAllCircumstancesForThisIsGodsWillForYouInChristJesus";
    public final static Long REFRESH_EXPIRY = 31536000000L;
    public final static Long ACCESS_EXPIRY = 604800000L;
  }

  public static class SOLAPI {

    public final static String API_KEY = "NCSJNJO5I8MV8UJU";
    public final static String API_SECRET_KEY = "2MUNKK1COFQ9HGWE5LBD58XQ2JQAU6EM";
    public final static String DOMAIN = "https://api.solapi.com";
    public final static String FROM_PHONE_NUMBER = "01088831954";
  }

  public static class VERIFICATION {

    public final static int MAX_CODE_GEN_NUM = 5;
    public final static int MAX_CODE_TRY_NUM = 5;
  }
}