package users;

public class UserNoEmail {

    private int id;
    private String name,DOB;


    public UserNoEmail(){}

    public UserNoEmail(int id, String name, String DOB) {
        this.id = id;
        this.name = name;
        this.DOB = DOB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNoEmail that = (UserNoEmail) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
