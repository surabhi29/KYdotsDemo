package entity;

import java.util.List;

public class StudentInformation {
    private String name;
    private String pursuing;
    private List<BackgroundDetails> backgrounDetails;
    private List<String> recommendation;
    private List<CompanyDetails> proffesional;
    private List<String> scholarships;
    private List<String> awards;
    private String proffesionalExperience;
    private List<String> socialWork;
    private String examName;
    private String examScore;
    private List<UniversityDetails> universities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPursuing() {
        return pursuing;
    }

    public void setPursuing(String pursuing) {
        this.pursuing = pursuing;
    }

    public List<BackgroundDetails> getBackgrounDetails() {
        return backgrounDetails;
    }

    public void setBackgrounDetails(List<BackgroundDetails> backgrounDetails) {
        this.backgrounDetails = backgrounDetails;
    }

    public List<String> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<String> recommendation) {
        this.recommendation = recommendation;
    }

    public List<CompanyDetails> getProffesional() {
        return proffesional;
    }

    public void setProffesional(List<CompanyDetails> proffesional) {
        this.proffesional = proffesional;
    }

    public List<String> getScholarships() {
        return scholarships;
    }

    public void setScholarships(List<String> scholarships) {
        this.scholarships = scholarships;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public String getProffesionalExperience() {
        return proffesionalExperience;
    }

    public void setProffesionalExperience(String proffesionalExperience) {
        this.proffesionalExperience = proffesionalExperience;
    }

    public List<String> getSocialWork() {
        return socialWork;
    }

    public void setSocialWork(List<String> socialWork) {
        this.socialWork = socialWork;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    public List<UniversityDetails> getUniversities() {
        return universities;
    }

    public void setUniversities(List<UniversityDetails> universities) {
        this.universities = universities;
    }

    @Override
    public String toString() {
        return "StudentInformation{" +
                "name='" + name + '\'' +
                ", pursuing='" + pursuing + '\'' +
                ", backgrounDetails=" + backgrounDetails +
                ", recommendation=" + recommendation +
                ", proffesional=" + proffesional +
                ", scholarships=" + scholarships +
                ", awards=" + awards +
                ", proffesionalExperience='" + proffesionalExperience + '\'' +
                ", socialWork=" + socialWork +
                ", examName='" + examName + '\'' +
                ", examScore='" + examScore + '\'' +
                ", universities='" + universities + '\'' +
                '}';
    }
}
