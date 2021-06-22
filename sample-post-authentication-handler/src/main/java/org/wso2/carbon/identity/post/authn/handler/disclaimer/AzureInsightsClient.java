package org.wso2.carbon.identity.post.authn.handler.disclaimer;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;

public class AzureInsightsClient {

    private static TelemetryClient telemetry;

    private AzureInsightsClient(){}

    public static synchronized TelemetryClient getInstance(){
        if (telemetry == null) {
            final String instrumentationKey = "<App-Insights-Key>";
            TelemetryConfiguration.getActive().setInstrumentationKey(instrumentationKey);
            telemetry = new TelemetryClient();
        }
        return telemetry;
    }
}
