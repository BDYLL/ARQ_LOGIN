package users;

import javax.json.Json;

public class UserToken {
    private String idToken;
    private String id;

    public UserToken() {
    }

    public UserToken(String idToken,String id) {
        this.idToken = idToken;
        this.id=id;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserToken userToken = (UserToken) o;

        return idToken.equals(userToken.idToken);
    }

    @Override
    public int hashCode() {
        return idToken.hashCode();
    }

    @Override
    public String toString() {
        return Json.createObjectBuilder()
                .add("idToken",idToken)
                .add("id",id)
                .build()
                .toString();
    }
}
