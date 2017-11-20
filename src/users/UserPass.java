package users;

import javax.json.Json;

public class UserPass extends FullUser {
    private String password;

    public UserPass() {
    }

    public UserPass(String id, String email, String name, String DOB, String password) {
        super(id, email, name, DOB);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return Json.createObjectBuilder()
                .add("id",getId())
                .add("email",getEmail())
                .add("DOB",getDOB())
                .add("password",password)
                .build()
                .toString();
    }

    public FullUser getFullUser(){
        return new FullUser(getId(),getEmail(),getName(),getDOB());
    }
}
