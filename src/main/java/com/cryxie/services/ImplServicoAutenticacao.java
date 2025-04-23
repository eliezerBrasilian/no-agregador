package com.cryxie.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cryxie.config.TokenJwt;
import com.cryxie.data.RespostaBase;
import com.cryxie.data.RespostaBase.Mensagem;
import com.cryxie.domain.discord.CartaoAutenticacao;
import com.cryxie.domain.discord.EntregadorDiscord;
import com.cryxie.repositories.UserRepository;
import com.cryxie.data.dtos.requests.LoginRequestDto;
import com.cryxie.data.dtos.responses.LoginResponseDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Getter
public class ImplServicoAutenticacao implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenJwt tokenServiceImpl;

    private AuthenticationManager authenticationManager;

    private EntregadorDiscord entregador = new EntregadorDiscord();

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var ModeloDeUsuarioOptional = userRepository.findByEmail(email.trim());
        if (ModeloDeUsuarioOptional.isPresent()) {
            var ModeloDeUsuario = ModeloDeUsuarioOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(ModeloDeUsuario.getUsername())
                    .password(ModeloDeUsuario.getPassword())
                    .authorities(ModeloDeUsuario.getAuthorities())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    public ResponseEntity<RespostaBase<LoginResponseDto>> login(LoginRequestDto data) throws IOException {
        authenticationManager = context.getBean(AuthenticationManager.class);
        var userOptional = this.userRepository.findByEmail(data.email());

        log.info("vai tentar logar");
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            if (auth.isAuthenticated()) {
                var user = userOptional.get();

                log.info("logou");

                var token = tokenServiceImpl.generateToken(user);

                String mensagem = """
                        usuário com o seguinte email logou com sucesso: %s
                        """.formatted(user.email);

                entregador.enviarNotificacao(new CartaoAutenticacao("operação de login", mensagem));

                return new RespostaBase<LoginResponseDto>(
                        new LoginResponseDto(
                                token,
                                user.email,
                                user.name,
                                user.creationDt,
                                user.id))
                        .ok();
            }

            return new RespostaBase<LoginResponseDto>(Mensagem.SENHA_INCORRETA).unauthorized();
        } catch (RuntimeException e) {
            log.error("tentativa de login fracassou {}", e.getMessage());
            entregador.enviarNotificacao(
                    new CartaoAutenticacao("crítico", "O usuário tentou logar com email inexistente " + data.email()));

            return new RespostaBase<LoginResponseDto>(Mensagem.USUARIO_COM_EMAIL_INFORMADO_NAO_EXISTE_OU_SENHA_INVALIDA)
                    .expectationFailed();

        }
    }

}