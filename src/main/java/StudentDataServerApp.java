import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.inject.Guice;
import com.google.inject.Injector;
import entity.ApplicationConfiguration;
import entity.DIModule;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import resource.CollegeResource;
import resource.StudentResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;


public class StudentDataServerApp extends Application<ApplicationConfiguration> {
    private static Log logger = LogFactory.getLog(StudentDataServerApp.class);
    private static ApplicationConfiguration configuration;

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        System.out.println("Initiliazing dictionary Server application");
        super.initialize(bootstrap);
        bootstrap.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        bootstrap.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) {
        StudentDataServerApp.configuration = configuration;
        System.out.println("Running StudentDataServerApp application");
        Injector injector = Guice.createInjector(DIModule.getInstance());
        environment.jersey().register(injector.getInstance(CollegeResource.class));
        environment.jersey().register(injector.getInstance(StudentResource.class));
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        configureCors(environment);
    }

    public static void main(String[] args) {
        try {
            logger.info("Initializing application");
            String[] appArgs = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                appArgs[i] = args[i];
            }
            new StudentDataServerApp().run(appArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization,Location");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_HEADERS_HEADER, "*");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_EXPOSE_HEADERS_HEADER, "*");
        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

    public static ApplicationConfiguration getConfiguration() {
        return configuration;
    }


}

