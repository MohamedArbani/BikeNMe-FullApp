package ma.ac.emi.ginfo.restfull.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.ac.emi.ginfo.restfull.entities.AuthRequest;
import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.UserRepository;
import ma.ac.emi.ginfo.restfull.services.JWTAuthenticationFilter;
import ma.ac.emi.ginfo.restfull.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // ...
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder());
//    }
//}
//

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserRepository userRepository;
//
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/**","*","/*","**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .logout();
////                .and()
////                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
////                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository));
//    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll()
                .antMatchers("/contact-us/**","/locations/**","/bikes/unknown/**","/files/**/**")
                .permitAll()
                .anyRequest().authenticated();

        // Add our custom JWT security filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder());
//    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

}

//class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        AuthRequest authRequest;
//        try {
//            authRequest = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            authRequest.getEmail(),
//                            authRequest.getPassword()
//                    )
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        User user = (User) authResult.getPrincipal();
//
//        String token = user.generateAuthToken();
//
//        response.addHeader("Authorization", "Bearer " + token);
//    }
//
//}
//
//class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//    private UserRepository userRepository;
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
//        super(authenticationManager);
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String header = request.getHeader("Authorization");
//
//        if (header == null || !header.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = header.replace("Bearer ", "");
//
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            Long userId = jwt.getClaim("userId").asLong();
//            User user = userRepository.findById(userId).orElse(null);
//
//
//            if (user == null) {
//                throw new Exception();
//            }
//
//            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
//        } catch (Exception e) {
//            SecurityContextHolder.clearContext();
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//}
