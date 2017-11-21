package users;

import javax.json.Json;

public class UserPass {
    private String password;
    private String email;
    private String name;
    private String DOB;

    public UserPass() {
    }

    public UserPass(String id, String email, String name, String DOB, String password) {
        this.password = password;
        this.name=name;
        this.email=email;
        this.DOB=DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
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
                .add("email",getEmail())
                .add("DOB",getDOB())
                .add("password",password)
                .build()
                .toString();
    }

    /*
    public FullUser getFullUser(){
        return new FullUser(,getEmail(),getName(),getDOB());
    }
    */
}
