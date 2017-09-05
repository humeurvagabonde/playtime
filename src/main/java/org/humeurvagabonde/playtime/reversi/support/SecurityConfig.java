package org.humeurvagabonde.playtime.reversi.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.HttpSecurity;
import org.springframework.security.core.userdetails.MapUserDetailsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.WebFilter;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    WebFilter springSecurityFilterChain(ReactiveAuthenticationManager manager) {
        HttpSecurity http = http();
        http.authenticationManager(manager);
        http.httpBasic();

        HttpSecurity.AuthorizeExchangeBuilder authorize = http.authorizeExchange();
        authorize.antMatchers("/admin/**").hasRole("ADMIN");
        authorize.antMatchers("/users/{user}/**").access(this::currentUserMatchesPath);
        authorize.anyExchange().authenticated();
        return http.build();
    }


    private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map( a -> context.getVariables().get("user").equals(a.getName()))
                .map( granted -> new AuthorizationDecision(granted));
    }

    @Bean
    public MapUserDetailsRepository userDetailsRepository() {
        UserDetails rob = User.withUsername("rob").password("rob").roles("USER").build();
        UserDetails admin = User.withUsername("admin").password("admin").roles("USER","ADMIN").build();
        return new MapUserDetailsRepository(rob, admin);
    }
}
