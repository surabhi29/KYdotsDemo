package entity;

public class CompanyDetails {
    private String companyName;
    private String designation;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "CompanyDetails{" +
                "companyName='" + companyName + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
