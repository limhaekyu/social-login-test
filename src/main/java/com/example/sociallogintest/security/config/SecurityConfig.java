package com.example.sociallogintest.security.config;

import com.example.sociallogintest.security.oauth2.CustomOAuth2UserService;
import com.example.sociallogintest.security.oauth2.OAuth2SuccessHandler;
import com.example.sociallogintest.security.token.JwtAuthFilter;
import com.example.sociallogintest.security.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/token/**","/").permitAll()
                    .anyRequest().authenticated()
                .and()
//                    .oauth2Login().loginPage("/token/expired")
                .oauth2Login().loginPage("/oauth2/authorization/kakao")
                        .successHandler(successHandler)
                        .userInfoEndpoint().userService(oAuth2UserService);

        http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }
}
