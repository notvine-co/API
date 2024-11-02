package dev.ianrich.kynos.web.construct;

import dev.ianrich.kynos.Kynos;
import org.json.JSONObject;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.body.BodyReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.util.Optional;

public class Request {
    private final RawHttpRequest rawHttpRequest;
    private JSONObject jsonBody;

    public Request(Socket socket) throws IOException {
        this.rawHttpRequest = new RawHttp().parseRequest(socket.getInputStream());
        parseJsonBody();
    }

    public String getMethod() {
        return this.rawHttpRequest.getMethod();
    }

    public URI getUri() {
        return this.rawHttpRequest.getUri();
    }

    public String getQuery() {
        return this.getUri().getQuery();
    }

    public Optional<InetAddress> getSenderAddress() {
        return this.rawHttpRequest.getSenderAddress();
    }

    public Optional<? extends BodyReader> getBody() {
        return this.rawHttpRequest.getBody();
    }

    private void parseJsonBody() {
        // Convert body to String and parse as JSON
        this.jsonBody = getBody()
                .flatMap(bodyReader -> {
                    try {
                        return Optional.of(bodyReader.decodeBodyToString(java.nio.charset.StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Optional.empty();
                    }
                })
                .map(JSONObject::new)
                .orElse(new JSONObject());  // Empty JSON if body is missing
    }

    public JSONObject getJsonRequest() {
        return jsonBody;
    }

    public boolean isAuthorized() {
        return getJsonRequest().has("authorization") && getJsonRequest().getString("authorization").equals(Kynos.mainConfig.getString("api-key"));
    }
}