package med.voll.api.infra.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    //metodo que ensina para o spring a injetar objetos
    @Bean
    //@Bean serve para uma exportar uma classe para o spring, fazendo ele carregar
    //e injetar em outras classes
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    //funcao para informar o spring que a senha é utilizando atraves do
    //hash bcrypt
    //123456 = $2a$12$iIAdFWDsyXIh8z88jt5CoON1fHH60jRpSJWgixrn8kvVs3u4vEUKu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
