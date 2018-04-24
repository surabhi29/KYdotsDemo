package entity;

public class CollegeDetails {
    private String collegeName;
    private String course;
    private String examName;
    private int  score;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "CollegeDetails{" +
                "collegeName='" + collegeName + '\'' +
                ", course='" + course + '\'' +
                ", examName='" + examName + '\'' +
                ", score=" + score +
                '}';
    }
}
