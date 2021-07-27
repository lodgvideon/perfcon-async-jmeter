package com.example;

import com.example.demo.model.DummyMessage;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.io.Serializable;

public class WaitCallEventSampler extends AbstractJavaSamplerClient implements Serializable {

    @lombok.SneakyThrows
    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        String expectedCallId = context.getParameter("CALL_ID");

        long timeout = context.getLongParameter("TIMEOUT");
        long startTime = context.getLongParameter("START_TIME");


        SampleResult result = new SampleResult();
        long started = System.currentTimeMillis();

        boolean found = false;
        DummyMessage dummyMessage = null;
        while (started + timeout > System.currentTimeMillis()) {


            dummyMessage = MessageHolder.getEvent(expectedCallId);
            if (dummyMessage != null) {
                found = true;
                MessageHolder.removeEvent(dummyMessage);
                break;

            } else {
                Thread.sleep(100);
                continue;
            }

        }
        if (!found) {
            result.setSuccessful(false);
            long elapsed = System.currentTimeMillis() - startTime;
            result.setStampAndTime(startTime, elapsed <= 0 ? 0 : elapsed);
            result.setResponseCode("500");
            result.setResponseMessage("Timeout exceeded while waiting for " + " expectedCallId:" + expectedCallId);
            return result;
        }

        long elapsed = dummyMessage.getTimestamp() - startTime;
        result.setStampAndTime(startTime, elapsed <= 0 ? 0 : elapsed);
        result.setSamplerData("Requesting for callId:" + expectedCallId);
        result.setSuccessful(true);
        result.setResponseCode("200");

        return result;
    }


    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();

        arguments.addArgument("CALL_ID", "${call_id}");
        arguments.addArgument("TIMEOUT", "10000");
        arguments.addArgument("START_TIME", "");
        return arguments;
    }


    @Override
    public void teardownTest(JavaSamplerContext context) {
        MessageHolder.clear();
    }

}
