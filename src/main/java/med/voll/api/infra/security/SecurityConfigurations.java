package med.voll.api.infra.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//====== STATELESS ======//
//stateless pq é uma API Rest

@Configuration
//personalizar configuracoes de seguraca
@EnableWebSecurity
public class SecurityConfigurations {

    //devolver um objeto (@Bean)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //vai desablitar o csrf pq o proprio TOKEN ja é uma medida de protecao para isso
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .build();

    }
}
