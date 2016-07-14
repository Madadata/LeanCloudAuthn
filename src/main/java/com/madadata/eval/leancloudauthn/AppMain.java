package com.madadata.eval.leancloudauthn;

import com.google.common.cache.CacheBuilderSpec;
import com.madadata.eval.leancloudauthn.api.AppUser;
import com.madadata.eval.leancloudauthn.auth.LeancloudAuthenticator;
import com.madadata.eval.leancloudauthn.auth.LeancloudAuthorizer;
import com.madadata.eval.leancloudauthn.client.LeancloudClient;
import com.madadata.eval.leancloudauthn.config.AppConfig;
import com.madadata.eval.leancloudauthn.health.LeancloudHealthCheck;
import com.madadata.eval.leancloudauthn.resource.LeancloudAuthenticationService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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
        return "LeancloudAuthentication";
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        LeancloudClient leancloudClient = appConfig.buildLeancloudClient(environment);
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<AppUser>()
                        .setAuthenticator(new CachingAuthenticator<>(
                                environment.metrics(),
                                new LeancloudAuthenticator(leancloudClient),
                                CacheBuilderSpec.parse(appConfig.getAuthCachingPolicy())))
                        .setAuthorizer(new LeancloudAuthorizer(leancloudClient))
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.healthChecks().register("leancloud", new LeancloudHealthCheck(leancloudClient));
        environment.jersey().register(new LeancloudAuthenticationService(leancloudClient));
    }

}
