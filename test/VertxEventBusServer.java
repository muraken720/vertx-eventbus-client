import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class VertxEventBusServer extends Verticle {

  public void start() {
    EventBus eb = vertx.eventBus();

    eb.registerHandler("some-address", new Handler<Message<JsonObject>>() {
      public void handle(Message<JsonObject> message) {
        JsonObject body = message.body();
        container.logger().info("receive message: " + body);

        JsonObject reply = new JsonObject().putString("action", "response")
            .putString("message", "This is a reply.");
        message.reply(reply);
      }
    });

    HttpServer server = vertx.createHttpServer();

    JsonObject config = new JsonObject().putString("prefix", "/eventbus");
    JsonArray permitted = new JsonArray().add(new JsonObject());
    vertx.createSockJSServer(server).bridge(config, permitted, permitted);

    server.listen(8080);
    container.logger().info(
        "Listening for eventbus at address: localhost:8080/eventbus");
  }
}


