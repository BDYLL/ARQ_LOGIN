package users;

public class UserNoID {

    private String email,name, DOB;

    public UserNoID(){}

    public UserNoID(String email, String name, String DOB) {
        this.email = email;
        this.name = name;
        this.DOB = DOB;
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

    @Override
    public String toString() {
        return "users.UserNoID{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", DOB='" + DOB + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNoID userNoID = (UserNoID) o;

        return email.equals(userNoID.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
