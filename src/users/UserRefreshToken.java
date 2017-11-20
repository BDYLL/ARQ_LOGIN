package users;

public class UserRefreshToken {
    private String refreshToken;

    public UserRefreshToken() {
    }

    public UserRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRefreshToken that = (UserRefreshToken) o;

        return refreshToken.equals(that.refreshToken);
    }

    @Override
    public int hashCode() {
        return refreshToken.hashCode();
    }

    @Override
    public String toString() {
        return "UserRefreshToken{}";
    }
}
