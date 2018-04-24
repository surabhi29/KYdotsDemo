package entity;

import io.dropwizard.Configuration;

public class ApplicationConfiguration extends Configuration {

    private static ApplicationConfiguration CONFIGURATION = null;

    public ApplicationConfiguration(){ CONFIGURATION = this; }

    public static ApplicationConfiguration getInstance() {
        return CONFIGURATION;
    }
}
