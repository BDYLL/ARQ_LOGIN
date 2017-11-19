package users;

import javax.json.Json;

public class FullUser {

    private String id;
    private String email;
    private String name;
    private String DOB;

    public FullUser() {
    }

    public FullUser(String id, String email, String name, String DOB) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.DOB = DOB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String toString(){
        return Json.createObjectBuilder()
                .add("id",this.id)
                .add("email",this.email)
                .add("name",this.name)
                .add("DOB",this.DOB)
                .build()
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullUser fullUser = (FullUser) o;

        return id == fullUser.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
