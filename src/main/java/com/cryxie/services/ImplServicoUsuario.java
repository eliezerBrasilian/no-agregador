package com.cryxie.services;

import org.springframework.stereotype.Service;
import com.cryxie.domain.discord.EntregadorDiscord;
import com.cryxie.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImplServicoUsuario {

    private final UserRepository userRepository;

    final EntregadorDiscord entregadorDiscord = new EntregadorDiscord();

    public ImplServicoUsuario(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long totalUsuarios() {
        return userRepository.count();
    }

}