package entity;

public class UniversityDetails {
    private String name;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UniversityDetails{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
