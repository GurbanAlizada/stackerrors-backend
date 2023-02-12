package com.stackerrors.config;


import com.stackerrors.security.JWTAccessDeniedHandler;
import com.stackerrors.security.JwtAuthenticationEntryPoint;
import com.stackerrors.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {



    private final JwtFilter jwtFilter;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtFilter jwtFilter,
                          JWTAccessDeniedHandler jwtAccessDeniedHandler,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }



    // hansiki permitAll ve admin huquqlari
    // varsa qeyd et zaten qalanlari isAuthenticated olur

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests((auth)->{   // will refactor
                    auth.antMatchers("/api/tag/admin/**" ).hasAuthority("ADMIN");
                    auth.antMatchers("/api/home").hasAnyAuthority("USER" , "ADMIN");
                    auth.antMatchers( HttpMethod.GET ,"/api/tag/**").permitAll();
                    auth.antMatchers( HttpMethod.GET ,"/api/question/**").permitAll();
                    auth.antMatchers( HttpMethod.GET ,"/api/error/**").permitAll();
                    auth.antMatchers( HttpMethod.PUT ,"/api/question/increaseViews/{id}").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/api/auth/login",
                "/api/user/register" ,
                "/api/auth/resetPassword",
                "/api/auth/logout",
                "/api/auth/forgotPassword",
                "/v2/api-docs/**" ,
                "/swagger.json" ,
                "/swagger-ui.html",
                "/swagger-resources/**" ,
                "/webjars/**");
    }



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }



}
