package db;

import users.*;

import java.util.List;

public class RedisHabitsDatabase implements HabitsDatabase {
    @Override
    public String addUser(FullUser newUser) {
        return "";
    }

    @Override
    public FullUser getUserByID(String id) {
        return null;
    }

    @Override
    public List<FullUser> getAllUsers() {
        return null;
    }

    @Override
    public boolean deleteUserByID(String id) {
        return false;
    }

    @Override
    public void deleteAllUsers() {

    }

    @Override
    public boolean updateUserByID(String id, BaseUser user) {
        return false;
    }

    @Override
    public String signup(UserPass user) {
        return null;
    }

    @Override
    public String authenticate(UserOnlyPass user) {
        return null;
    }

    @Override
    public String refreshToken(UserRefreshToken userRefreshToken) {
        return null;
    }

}
