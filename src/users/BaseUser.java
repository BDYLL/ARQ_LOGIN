package users;

import javax.json.Json;

public class BaseUser {

    private String name,DOB;

    public BaseUser() {
    }

    public BaseUser(String name, String DOB) {
        this.name = name;
        this.DOB = DOB;
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

    @Override
    public String toString() {
        return Json.createObjectBuilder().add("name",name).add("DOB",DOB).build().toString();
    }
}
