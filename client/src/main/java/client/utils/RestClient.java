package client.utils;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class RestClient {
    private final Client client;
    private String serverPath;

    public RestClient() {
        client = ClientBuilder.newClient(new ClientConfig());
    }

    public void changeServer(String serverAddress) {
        serverPath = "http://" + serverAddress + "/";
    }


    public <T, R> T executeRequest(RequestType requestType, R entity, GenericType<T> responseType, String path){
        WebTarget target = client.target(serverPath).path(path);
        Invocation.Builder builder = target.request(APPLICATION_JSON).accept(APPLICATION_JSON);

        switch (requestType) {
            case GET:
                return builder.get(responseType);
            case PUT:
                if(responseType == null) {
                    builder.put(Entity.entity(entity, APPLICATION_JSON));
                    return null;
                } return builder.put(Entity.entity(entity, APPLICATION_JSON), responseType);
            case POST:
                return builder.post(Entity.entity(entity, APPLICATION_JSON), responseType);
            case DELETE:
                builder.delete();
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Response longPolling(String path) {
        WebTarget target = client.target(serverPath).path(path);
        return target.request(APPLICATION_JSON).get();
    }

    public boolean isServerRunning() {
        try {
            ClientBuilder.newClient(new ClientConfig())
                    .target(serverPath)
                    .request().get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
