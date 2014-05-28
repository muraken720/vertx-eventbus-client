import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.EventBusBridgeHook;
import org.vertx.java.core.sockjs.SockJSSocket;
import org.vertx.java.platform.Verticle;

import java.util.HashSet;
import java.util.Set;

public class VertxEventBusServer extends Verticle implements EventBusBridgeHook{

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
    vertx.createSockJSServer(server).setHook(this)
        .bridge(config, permitted, permitted);

    server.listen(8080);
    container.logger().info(
        "Listening for eventbus at address: localhost:8080/eventbus");
  }

  public boolean handleSocketCreated(SockJSSocket sock){
    container.logger().info("handleSocketCreated: " + sock.writeHandlerID());
    return true;
  }

  public void handleSocketClosed(SockJSSocket sock){
    container.logger().info("handleSocketClosed: " + sock.writeHandlerID());
  }

  public boolean handleSendOrPub(SockJSSocket sock, boolean send, JsonObject msg, String address){
    container.logger().info("handleSendOrPub: " + sock.writeHandlerID());
    return true;
  }

  public boolean handlePreRegister(SockJSSocket sock, String address){
    container.logger().info("handlePreRegister: " + sock.writeHandlerID());
    return true;
  }

  public void handlePostRegister(SockJSSocket sock, String address){
    container.logger().info("handlePostRegister: " + sock.writeHandlerID());
  }

  public boolean handleUnregister(SockJSSocket sock, String address){
    container.logger().info("handleUnregister: " + sock.writeHandlerID());
    return true;
  }

  public boolean handleAuthorise(JsonObject message, String sessionID,
                                 Handler<AsyncResult<Boolean>> handler){
    container.logger().info("handleAuthorise: " + sessionID);
    return true;
  }
}
