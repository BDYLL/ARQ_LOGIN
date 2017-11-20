package users;

import javax.json.Json;

public class UserOnlyPass {

    private String email,password;

    public UserOnlyPass() {
    }

    public UserOnlyPass(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return Json.createObjectBuilder().add("email",email).add("password",password).build().toString();
    }
}
