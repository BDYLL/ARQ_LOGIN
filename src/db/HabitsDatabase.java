package db;

import users.*;

import java.util.List;

public interface HabitsDatabase {

    int addUser(UserNoID newUser);

    FullUser getUserByID(int id);
    FullUser getUserByEmail(String email);
    List<FullUser> getAllUsers();

    boolean deleteUserByID(int id);
    boolean deleteUserByEmail(String email);
    void deleteAllUsers();

    boolean updateUserByID(int id);
    boolean updateUserByEmail(String email);

    boolean connect();

    boolean authenticate(String email,String hash);
}
