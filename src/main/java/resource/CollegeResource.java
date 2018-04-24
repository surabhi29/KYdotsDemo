package resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import crawler.WebsiteCrawler;
import dao.CollegeDetailsDao;
import entity.CollegeDetails;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Singleton
@Path("/collegedetails")
@Produces(MediaType.APPLICATION_JSON)
public class CollegeResource {

    @Inject
    private ObjectMapper mapper;
    @Inject
    private CollegeDetailsDao collegeDetailsDao;

    @POST
    public Response uploadCollegeData(String body){
        System.out.println("post::" + body);
        Map<String, String> map;
        if(StringUtils.isNotEmpty(body)) {
            try {
                map = mapper.readValue(body, new TypeReference<Map<String, String>>() {});
                CollegeDetails collegeDetails = WebsiteCrawler.getTitle(map.get("url"));
                if(collegeDetails != null)
                    collegeDetailsDao.getUniqueCategoryPath(collegeDetails);
            } catch (IOException e) {
                System.out.println("Error in formatting the input data");
            }
        }
        return Response.status(200).build();
    }
}
