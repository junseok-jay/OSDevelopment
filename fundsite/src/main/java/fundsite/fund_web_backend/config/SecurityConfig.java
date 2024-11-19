package fundsite.fund_web_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fundsite.fund_web_backend.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.userDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // 개발 단계에서 CSRF 보호 비활성화 (운영 시 주의)
            .authorizeHttpRequests()
                .requestMatchers("/", "/donations/**", "/register", "/login", "/images/**").permitAll() // 홈페이지와 기부 정보는 모두 접근 가능
                .requestMatchers("/mypage").authenticated() // 마이페이지는 로그인된 사용자만 접근 가능
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login") // 로그인 폼의 액션 URL과 일치시킴
                .defaultSuccessUrl("/mypage", true)
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll();

        return http.build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(NoOpPasswordEncoder.getInstance()); // 비밀번호 암호화하지 않음 (개발 중 테스트용)
    }
}
