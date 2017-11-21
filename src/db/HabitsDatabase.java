package db;

import users.*;

import java.util.List;

public interface HabitsDatabase {

    String addUser(FullUser newUser);

    void deleteAllUsers();

    List<FullUser> getAllUsers();

    FullUser getUserByID(String id);

    boolean deleteUserByID(String id);

    boolean updateUserByID(String id, BaseUser user);

    String signup(UserPass user);

    String authenticate(UserOnlyPass user);

    String refreshToken(UserRefreshToken userRefreshToken);

    String deleteUser(UserToken token);

}
