package resource;

import com.google.inject.Inject;
import dao.StudentDao;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Singleton
@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    @Inject
    private StudentDao studentDao;

    @POST
    public Response uploadFile(@QueryParam("fileName") String fileName) throws IOException {
        studentDao.upload(fileName);
        return Response.status(200).build();
    }
}

