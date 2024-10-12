package controller;

/**
 * This class represents security variables used for managing user authentication and authorization.
 * These variables help in keeping track of user login status, user roles, and user IDs.
 */
public class SecurityVariable {
    public static boolean isUserLoggedIn = false;
    public static boolean isUserNormal = false;
    public static boolean isUserAdmin = false;
    public static long loggedUserId=0;
    public static Long mainDPSId = 0L;
    public static Long subCharacterId1 = 0L;
    public static Long subCharacterId2 = 0L;
    public static Long subCharacterId3 = 0L;

}
