package by.epam.training.util;

public class SqlRequest {
    public static final String FIND_PROFILE_BY_LOGIN_AND_PASSWORD =
            "SELECT id, login, password, role FROM profiles WHERE login =? AND password = SHA1(?)";
    public static final String CHECK_USER_EXISTS =
            "SELECT id FROM profiles WHERE login = ?";
    public static final String INSERT_USER =
            "INSERT INTO profiles(login, password, role) VALUES (?, SHA1(?), ?)";
    public static final String FIND_PROFILE_BY_LOGIN =
            "SELECT id, login, password, role FROM profiles WHERE login = ?";
    public static final String SQL_CHECK_USER_MATCHES =
            "SELECT login, password FROM profiles WHERE login =? AND password = SHA1(?)";
    public static final String SQL_CHANGE_USER_PASSWORD =
            "UPDATE profiles SET password = SHA1(?) WHERE login = ?";
}
