package common;


import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.transport.TransportClient;

import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

@Singleton
public class ESConnection {
    private static Log logger = LogFactory.getLog(ESConnection.class);
    private static TransportClient client;

    private ESConnection() {}

    public static TransportClient createConnection() {
        logger.info("creating ES connection");
        if (client == null) {
            Settings settings = Settings.builder()
                    .put("cluster.name", "elasticsearch").build();
            try {
                client  = new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
                return client;
            }  catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
        return client;
    }

    public static TransportClient getClient() {
        return client;
    }
}