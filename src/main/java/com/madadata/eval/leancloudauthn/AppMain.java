package com.madadata.eval.leancloudauthn;

import com.madadata.eval.leancloudauthn.config.AppConfig;
import com.madadata.eval.leancloudauthn.resource.LeancloudAuthenticationService;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

/**
 * Created by jiayu on 7/14/16.
 */
public class AppMain extends Application<AppConfig> {

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        final Client client = new JerseyClientBuilder(environment).using(appConfig.getJerseyClientConfiguration())
                .build(getName());
        environment.jersey().register(new LeancloudAuthenticationService(client));
    }

}
