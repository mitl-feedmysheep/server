package feedmysheep.feedmysheepapi.global.policy;

public class CONSTANT {
  public static class JWT {
    public final static String SECRET_KEY = "RejoiceAlwaysPrayContinuallyGiveThanksInAllCircumstancesForThisIsGodsWillForYouInChristJesus";
    public final static Long REFRESH_EXPIRY = 31536000000L;
    public final static Long ACCESS_EXPIRY = 86400000L;
  }

  public static class TWILIO {
    public final static String ACCOUNT_SID = "AC8edd3e64ee51444b1a3ea11d5268edee";
    public final static String AUTH_TOKEN = "c63a826d7c5e799748ce4a9e78900a5a";
    public final static String FROM_PHONE_NUMBER = "+17067703741";
  }

  public static class VERIFICATION {
    public final static int MAX_CODE_GEN_NUM = 5;
    public final static int MAX_CODE_TRY_NUM = 5;
  }
}