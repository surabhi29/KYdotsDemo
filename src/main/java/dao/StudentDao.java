package dao;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import common.ESConnection;
import entity.BackgroundDetails;
import entity.CompanyDetails;
import entity.StudentInformation;
import entity.UniversityDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import javax.inject.Singleton;
import java.io.*;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Singleton
public class StudentDao {
    private static final HashFunction murmurHash = Hashing.murmur3_128();
    private static Log logger = LogFactory.getLog(StudentDao.class);
    private static TransportClient client = ESConnection.getClient();
    public void upload(String fileName) throws IOException {
        Map<String, StudentInformation> studentInfo = new LinkedHashMap<>();
        if(StringUtils.isNotEmpty(fileName)) {
            File f = new File("inputFolder/" + fileName);
            if (f.exists()) {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(f));
                BufferedReader buf = new BufferedReader(inputStreamReader);
                String line;
                int read=0;
                while((line=buf.readLine()) != null) {
                    if(read > 0) {
                        String[] backgroundDetails = null; String[] proffesional =null;
                        String[] str = line.split("\t");
                        String name = str[1];
                        String pursuing = str[2];
                        if(!str[3].equals("n/a"))
                            backgroundDetails = str[3].split(",");
                        String recommendation = str[4];
                        if(!str[5].equals("n/a"))
                            proffesional = str[5].split(",");
                        String scholarships = str[6];
                        String awards = str[7];
                        String proffesionalExperience = str[8];
                        String socialWork = str[9];
                        String examName = str[10];
                        String examScore = str[11];
                        String universities = str[12];
                        BackgroundDetails background = new BackgroundDetails();
                        if(backgroundDetails.length > 0 && !backgroundDetails[0].equals("n/a")) {
                            background.setCourseName(backgroundDetails[0]);
                            background.setCollegeName(backgroundDetails[1]);
                            background.setPercentage(backgroundDetails[2]);
                        }

                        CompanyDetails companyDetails = new CompanyDetails();
                        if(proffesional != null) {
                            if (proffesional.length > 0 && !proffesional[0].equals("n/a")) {
                                companyDetails.setCompanyName(proffesional[0]);
                                companyDetails.setDesignation(proffesional[1]);
                            }
                        }

                        UniversityDetails universityDetails = new UniversityDetails();
                        if(universities != null) {
                            if (!universities.equals("n/a")) {
                                universityDetails.setName(universities);
                                universityDetails.setStatus("applied");
                            }
                        }

                        if(!studentInfo.containsKey(str[0])) {
                            studentInfo.putIfAbsent(str[0], compute(name, pursuing, background, recommendation,
                                    companyDetails, scholarships, awards, proffesionalExperience,
                                    socialWork, examName, examScore, universityDetails));
                        }else {

                            studentInfo.computeIfPresent(str[0], (key, value) -> computeIfPresent(value, key, name, pursuing, background, recommendation,
                                    companyDetails, scholarships, awards, proffesionalExperience,
                                    socialWork, examName, examScore, universityDetails));
                        }
                    }
                    read++;
                }
                studentInfo.entrySet().stream().forEach(System.out::println);
                uploadES(studentInfo);
            }
        }
    }


    public StudentInformation compute(String name, String pursuing, BackgroundDetails background, String recommendation,
                                      CompanyDetails companyDetails, String scholarships, String awards, String proffesionalExperience,
                                      String socialWork, String examName, String examScore, UniversityDetails universities) {

        StudentInformation studentInformation = new StudentInformation();
        if(!name.equals("n/a"))
            studentInformation.setName(name);
        if(!pursuing.equals("n/a"))
            studentInformation.setPursuing(pursuing);
        if(background != null) {
            List list = new ArrayList();
            list.add(background);
            studentInformation.setBackgrounDetails(list);
        }
        if(!recommendation.equals("n/a")) {
            List list = new ArrayList();
            list.add(recommendation);
            studentInformation.setRecommendation(list);
        }
        if(companyDetails != null) {
            List list = new ArrayList();
            list.add(companyDetails);
            studentInformation.setProffesional(list);
        }
        if(!scholarships.equals("n/a")) {
            List list = new ArrayList();
            list.add(scholarships);

            studentInformation.setScholarships(list);
        }
        if(!awards.equals("n/a")) {
            List list = new ArrayList();
            list.add(awards);
            studentInformation.setAwards(list);
        }
        if(!proffesionalExperience.equals("n/a"))
            studentInformation.setProffesionalExperience(proffesionalExperience);
        if(!scholarships .equals("n/a")) {
            List list = new ArrayList();
            list.add(socialWork);
            studentInformation.setSocialWork(list);
        }
        if(!examScore.equals("n/a"))
            studentInformation.setExamScore(examScore);
        if(!examName.equals("n/a"))
            studentInformation.setExamName(examName);
        if(!universities.equals("n/a")) {
            List list = new ArrayList();
            list.add(universities);
            studentInformation.setUniversities(list);
        }
        return studentInformation;
    }

    public StudentInformation computeIfPresent(StudentInformation studentInformation, String id, String name, String pursuing,
                                               BackgroundDetails background, String recommendation,
                                               CompanyDetails companyDetails, String scholarships, String awards,
                                               String proffesionalExperience, String socialWork, String examName,
                                               String examScore, UniversityDetails universities) {

        List<BackgroundDetails> backgrounDetails = studentInformation.getBackgrounDetails();
        if(background.getCollegeName() != null)
            backgrounDetails.add(background);
        studentInformation.setBackgrounDetails(backgrounDetails);
        List<String> recommendation1 = studentInformation.getRecommendation();
        if(!recommendation.equals("n/a"))
            recommendation1.add(recommendation);
        studentInformation.setRecommendation(recommendation1);
        List<CompanyDetails> proffesional = studentInformation.getProffesional();
        if(companyDetails.getCompanyName() != null)
            proffesional.add(companyDetails);
        studentInformation.setProffesional(proffesional);
        List<String> scholarships1 = studentInformation.getScholarships();
        if(!scholarships.equals("n/a"))
            scholarships1.add(scholarships);
        studentInformation.setScholarships(scholarships1);
        List<String> awards1 = studentInformation.getAwards();
        if(!awards.equals("n/a"))
            awards1.add(awards);
        studentInformation.setAwards(awards1);
        List<String> socialWork1 = studentInformation.getSocialWork();
        if(!socialWork.equals("n/a"))
            socialWork1.add(socialWork);
        studentInformation.setSocialWork(socialWork1);
        List<UniversityDetails> universities1 = studentInformation.getUniversities();
        if(universities.getName() != null)
            universities1.add(universities);
        studentInformation.setUniversities(universities1);
        return studentInformation;
    }

    public void uploadES(Map<String, StudentInformation> map) {

        map.entrySet().stream().forEach((data) -> {
            StringBuilder id = new StringBuilder();
            id.append(murmurHash.hashBytes(data.getKey().toLowerCase().getBytes()));
            XContentBuilder contentBuilder = null;
            try {
                contentBuilder = jsonBuilder().startObject().prettyPrint();
                StudentInformation information = data.getValue();
                contentBuilder.field("name", information.getName().toLowerCase());
                contentBuilder.field("pursuing", information.getPursuing().toLowerCase());
                List<Map<String, String>> background = new ArrayList<>();
                information.getBackgrounDetails().forEach((value) -> {
                    Map<String, String> backgroundMap = new LinkedHashMap<>();
                    backgroundMap.put("courseName", value.getCourseName());
                    backgroundMap.put("collegeName", value.getCollegeName());
                    backgroundMap.put("percentage", value.getPercentage());
                    background.add(backgroundMap);
                });
                contentBuilder.field("backgroundDetails", background);
                contentBuilder.field("awards", information.getAwards());
                contentBuilder.field("examName", information.getExamName());
                contentBuilder.field("examScore", information.getExamScore());
                contentBuilder.field("experience", information.getProffesionalExperience());
                List<Map<String, String>> professional = new ArrayList<>();
                information.getProffesional().forEach((value) -> {
                    Map<String, String> profMap = new LinkedHashMap<>();
                    profMap.put("companyName", value.getCompanyName());
                    profMap.put("designation", value.getDesignation());
                    professional.add(profMap);
                });
                contentBuilder.field("proffessionalDetails", professional);
                contentBuilder.field("recommendations", information.getRecommendation());
                contentBuilder.field("scholarships", information.getScholarships());
                contentBuilder.field("socialWork", information.getSocialWork());
                List<Map<String, String>> university = new ArrayList<>();
                information.getUniversities().forEach((value) -> {
                    Map<String, String> universityMap = new LinkedHashMap<>();
                    universityMap.put("universityName", value.getName());
                    String status = updateCollgeStatus(value.getName(), information.getPursuing(), information.getExamScore());
                    if(status != null)
                         universityMap.put("status", status);
                    else
                        universityMap.put("status", value.getStatus());
                    university.add(universityMap);
                });
                contentBuilder.field("universityDetails", university);
                contentBuilder.endObject();
            } catch (IOException e) {
                System.out.println("Error while adding contentFielder");
            }
            IndexRequest indexRequest = new IndexRequest("studentdetails", "studentdetails", id.toString());
            indexRequest.source(contentBuilder);

            IndexResponse indexResponse = client.index(indexRequest).actionGet();
            if(indexResponse.status().getStatus() == 200){
//                System.out.println("Successfully uploaded");
                logger.info("Successfully uploaded");
            }
        });
    }

    public String updateCollgeStatus(String collgeName, String course, String studentMarks) {
        String status=null;
        StringBuilder id = new StringBuilder();
        id.append(murmurHash.hashBytes((collgeName.toLowerCase() + "^" + course.toLowerCase()).getBytes()));
        GetResponse getResponse = client.get(new GetRequest("collegedetails", "collegedetails", id.toString())).actionGet();
        if(getResponse.isExists()) {
            Object score = client.get(new GetRequest("collegedetails", "collegedetails", id.toString()))
                        .actionGet().getSourceAsMap().get("score");
            if(Integer.parseInt(studentMarks) >= (Integer)score) {
//                System.out.println("status::" + "accepted");
                status = "accepted";
            }else {
                status ="rejected";
            }
        }
        return status;
    }
}

