package websocket;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class WebSocketSimulation0 extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .wsBaseUrl("ws://localhost:7878");

    ScenarioBuilder scene = scenario("WebSocket")
            .exec(ws("Connect WS").connect("/"))
            .pause(1)
            .exec(ws("Say hi")
                    .sendText("message Hi")
                    .await(30).on(
                            ws.checkTextMessage("checkMessage")
                                    .check(regex(".*message Hi*"))))
            .pause(1)
            .exec(ws("Close WS").close());

    {
        setUp(scene.injectOpen(
                        atOnceUsers(1000))
                .protocols(httpProtocol));
    }
}

