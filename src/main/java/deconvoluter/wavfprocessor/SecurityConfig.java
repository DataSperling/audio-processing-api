package deconvoluter.wavfprocessor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers("/waveforms/**")
        .hasRole("WAVEFORM-OWNER")
        .and()
        .csrf().disable()
        .httpBasic();

    return http.build();
  }

  @Bean
  public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
    User.UserBuilder users = User.builder();
    UserDetails dataSperling = users
        .username("data-sperling")
        .password(passwordEncoder.encode("nesty-1"))
        .roles("WAVEFORM-OWNER")
        .build();
    UserDetails cuckooNoSong = users
        .username("cuckoo-no-song")
        .password(passwordEncoder.encode("yzg-798"))
        .roles("NON-OWNER")
        .build();
    return new InMemoryUserDetailsManager(dataSperling, cuckooNoSong);

  }

  @Bean
  public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
