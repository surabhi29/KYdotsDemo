package crawler;

import entity.CollegeDetails;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebsiteCrawler {
    public static CollegeDetails getTitle(String address) throws IOException {
        if(StringUtils.isNotEmpty(address)) {
            String[] split = address.split("/");
            int length = split.length;
            String collegeName = split[length - 1].substring(0, split[length - 1].indexOf("_mba"));
            CollegeDetails collegeDetails = new CollegeDetails();
            collegeDetails.setCollegeName(collegeName);
            String course = "mba";
            collegeDetails.setCourse(course);
            System.out.println("collegeName::"    + collegeName);

            Document doc = Jsoup.connect(address).get();
            Elements active = doc.getElementsByClass("active");
            if (active.select("tr") != null) {
                Element tr = active.select("tr").first();
                tr.select("td").forEach((s) -> System.out.println(s.text()));
                String examName = tr.select("td").first().text().split(" ")[1];
                collegeDetails.setExamName(examName);
                int score = Integer.parseInt(tr.select("td").last().text());
                collegeDetails.setScore(score);
                System.out.println("collegeDetails::" + collegeDetails);
            }
            return collegeDetails;
        }
        return  null;
    }

    /*public static void main(String[] args) throws IOException {
        getTitle("http://www.mbaapplicant.com/mba_class_profiles/harvard_mba_class_profile.html");
        getTitle("http://www.mbaapplicant.com/mba_class_profiles/chicago_booth_mba_class_profile.html");
    }*/
}

