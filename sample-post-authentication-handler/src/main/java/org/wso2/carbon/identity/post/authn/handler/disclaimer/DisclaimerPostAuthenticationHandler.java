/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.post.authn.handler.disclaimer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.AbstractPostAuthnHandler;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.PostAuthnHandlerFlowStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class DisclaimerPostAuthenticationHandler extends AbstractPostAuthnHandler {

    private static final Log log = LogFactory.getLog(DisclaimerPostAuthenticationHandler.class);


    @Override
    public PostAuthnHandlerFlowStatus handle(HttpServletRequest httpServletRequest,
                                             HttpServletResponse httpServletResponse,
                                             AuthenticationContext authenticationContext) {

        log.info("handling post authentication...");
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        String hostName;
        try {
            hostName = InetAddress.getByName(httpServletRequest.getHeader("x-forwarded-for")).getHostName();

            if (!ip.equalsIgnoreCase(hostName)) {
                AzureInsightsClient.getInstance().trackEvent("trackedHostName",
                        Map.of("hostName", hostName), null);
            }

        } catch (UnknownHostException e) {
            log.error("failed to resolve host name for the IP:" + ip, e);
            System.out.println(e.getMessage());
        }

        return PostAuthnHandlerFlowStatus.SUCCESS_COMPLETED;
    }

    @Override
    public String getName() {

        return "DisclaimerHandler";
    }

}
