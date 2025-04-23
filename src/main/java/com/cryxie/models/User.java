package com.cryxie.models;

import com.cryxie.data.dtos.requests.AuthRegisterRequestDto;
import com.cryxie.data.dtos.responses.UserResponseDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    public String id;
    public String name = "";
    public String email = "";
    public String password = "";
    public LocalDateTime creationDt = LocalDateTime.now();
    Sexo sexo;

    public enum Sexo {
        F, M
    }

    public User(AuthRegisterRequestDto solicitacaoDeAutenticacaoDto) {
        this.email = solicitacaoDeAutenticacaoDto.email();
        this.password = new BCryptPasswordEncoder().encode(solicitacaoDeAutenticacaoDto.senha());
        this.name = solicitacaoDeAutenticacaoDto.nome();
        this.creationDt = LocalDateTime.now();
    }

    public UserResponseDto toResponseDto() {
        return new UserResponseDto(
                id,
                name,
                email);
    }

    public static enum Role {
        USUARIO
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USUARIO.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUserMocked() {
        return new User(
                new AuthRegisterRequestDto(
                        "usuario@teste.com",
                        "12345678",
                        "Neymar Junior"));

    }
}