package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.io.Serializable;

@Slf4j
public class InitWebSocketSessionSample extends AbstractJavaSamplerClient implements Serializable {

    @Override
    public SampleResult runTest(JavaSamplerContext ctx) {
        String uri = ctx.getParameter("URI");
        SampleResult result = new SampleResult();
        result.sampleStart();
        if (uri == null | uri.isEmpty()) {
            result.setSuccessful(false);
            result.setResponseMessage("URL property is empty");
            result.sampleEnd();
            return result;
        }

        WebsocketClientInitializer websocketClientInitializer = new WebsocketClientInitializer(uri);

        try {
            SessionHolder.addSession(RandomStringUtils.randomAlphanumeric(10), websocketClientInitializer.open());
            result.setSuccessful(true);
            result.setResponseMessage("Ok");
        } catch (Error e) {
            log.error(new StringBuilder().append("Error during opening connection to: ").append(uri).append("\n ").append(e.getLocalizedMessage()).toString());
            result.setSuccessful(false);
            result.setResponseMessage("Problem during Opening channel: " + e.getLocalizedMessage());
        }
        result.sampleEnd();
        return result;
    }


    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("URI", "ws://127.0.0.1:9102/websocket");
        return arguments;
    }


    @Override
    public void teardownTest(JavaSamplerContext context) {
        SessionHolder.closeAllSessions();
    }

}
