package dao;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import common.ESConnection;
import entity.CollegeDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Singleton
public class CollegeDetailsDao {
    @Inject
    private static Log logger = LogFactory.getLog(CollegeDetailsDao.class);
    private static TransportClient client = ESConnection.getClient();
    private static final HashFunction murmurHash = Hashing.murmur3_128();

    public void getUniqueCategoryPath(CollegeDetails collegeDetails){
        StringBuilder id = new StringBuilder();
        id.append(murmurHash.hashBytes((collegeDetails.getCollegeName().toLowerCase() + "^" + collegeDetails.getCourse().toLowerCase()).getBytes()));
        XContentBuilder contentBuilder = null;
        try {
            contentBuilder = jsonBuilder().startObject().prettyPrint();
            contentBuilder.field("collegeName", collegeDetails.getCollegeName().toLowerCase());
            contentBuilder.field("course", collegeDetails.getCourse().toLowerCase());
            contentBuilder.field("examName", collegeDetails.getExamName().toLowerCase());
            contentBuilder.field("score", collegeDetails.getScore());
            contentBuilder.endObject();
        } catch (IOException e) {
            System.out.println("Error while adding contentFielder");
        }
        IndexRequest indexRequest = new IndexRequest("collegedetails", "collegedetails", id.toString());
        indexRequest.source(contentBuilder);

        IndexResponse indexResponse = client.index(indexRequest).actionGet();
        if(indexResponse.status().getStatus() == 200){
            System.out.println("Successfully uploaded");
            logger.info("Successfully uploaded");
        }
    }
}
