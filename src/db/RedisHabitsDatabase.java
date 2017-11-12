package db;

import users.FullUser;
import users.UserNoID;

import java.util.List;

public class RedisHabitsDatabase implements HabitsDatabase {
    @Override
    public int addUser(UserNoID newUser) {
        return 0;
    }

    @Override
    public FullUser getUserByID(int id) {
        return null;
    }

    @Override
    public FullUser getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<FullUser> getAllUsers() {
        return null;
    }

    @Override
    public boolean deleteUserByID(int id) {
        return false;
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        return false;
    }

    @Override
    public void deleteAllUsers() {

    }

    @Override
    public boolean updateUserByID(int id) {
        return false;
    }

    @Override
    public boolean updateUserByEmail(String email) {
        return false;
    }

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean authenticate(String email, String hash) {
        return false;
    }
}
