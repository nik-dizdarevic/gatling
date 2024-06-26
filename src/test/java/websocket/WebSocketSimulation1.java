package websocket;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

// Set up the simulation with a more gradual ramp-up
public class WebSocketSimulation1 extends Simulation {

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
        setUp(
                scene.injectOpen(
                        rampUsers(10000).during(30)
                )
        ).protocols(httpProtocol);
    }
}

