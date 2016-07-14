package com.madadata.eval.leancloudauthn;

import com.madadata.eval.leancloudauthn.config.AppConfig;
import com.madadata.eval.leancloudauthn.health.LeancloudHealthCheck;
import com.madadata.eval.leancloudauthn.resource.LeancloudAuthenticationService;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

/**
 * Created by jiayu on 7/14/16.
 */
public class AppMain extends Application<AppConfig> {

    public static void main(String[] args) throws Exception {
        new AppMain().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public String getName() {
        return "Leancloud Authentication";
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        final Client client = new JerseyClientBuilder(environment)
                .using(appConfig.getJerseyClientConfiguration())
                .build(getName());
        environment.healthChecks().register("leancloud", new LeancloudHealthCheck(client, appConfig.getLeancloud()));
        environment.jersey().register(new LeancloudAuthenticationService(client, appConfig.getLeancloud()));
    }

}
