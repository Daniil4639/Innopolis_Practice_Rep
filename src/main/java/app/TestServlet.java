package app;

import io.undertow.Undertow;

public class TestServlet {

    public static void main(String[] args) {
        Undertow server = Undertow
                .builder()
                .addHttpListener(8080, "localhost")
                .setHandler(exchange -> {
                    exchange.getResponseSender()
                            .send("\"Test servlet container is working!\"");
                })
                .build();

        server.start();
    }
}