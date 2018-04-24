package entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import common.ESConnection;
import org.elasticsearch.client.transport.TransportClient;

public class DIModule extends AbstractModule {

    private static final DIModule INSTANCE = new DIModule();

    private DIModule() {}

    public static DIModule getInstance() { return INSTANCE; }

    @Override
    protected void configure() {

        bind(ObjectMapper.class).asEagerSingleton();
        bind(TransportClient.class).toInstance(ESConnection.createConnection());
    }
}