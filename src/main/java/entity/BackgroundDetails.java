package entity;

public class BackgroundDetails {
    private String courseName;
    private String collegeName;
    private String percentage;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "BackgroundDetails{" +
                "courseName='" + courseName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", percentage='" + percentage + '\'' +
                '}';
    }
}
