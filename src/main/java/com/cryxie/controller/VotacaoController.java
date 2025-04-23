package com.cryxie.controller;

import com.cryxie.models.Movie;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class VotacaoController {

    private static final Logger logger = Logger.getLogger(VotacaoController.class.getName());
    private final Map<String, Movie> movies = new HashMap<>();

    public VotacaoController() {
        logger.info("Inicializando VotacaoController...");
        // Inicializando alguns filmes para teste
        Movie movie1 = new Movie();
        movie1.setId(UUID.randomUUID().toString());
        movie1.setNome("O Poderoso Chefão");
        movie1.setTotalVotos(0);
        movie1.setDescription("Um clássico do cinema");
        movie1.setCreationDt(LocalDateTime.now());

        Movie movie2 = new Movie();
        movie2.setId(UUID.randomUUID().toString());
        movie2.setNome("O Senhor dos Anéis");
        movie2.setTotalVotos(0);
        movie2.setDescription("Uma aventura épica");
        movie2.setCreationDt(LocalDateTime.now());

        movies.put(movie1.getId(), movie1);
        movies.put(movie2.getId(), movie2);
        logger.info("Filmes iniciais criados: " + movies.size());
    }

    @MessageMapping("/votar")
    @SendTo("/topic/movies")
    public Map<String, Movie> votar(String movieId) {
        logger.info("Recebido voto para o filme: " + movieId);
        Movie movie = movies.get(movieId);
        if (movie != null) {
            movie.setTotalVotos(movie.getTotalVotos() + 1);
            logger.info("Voto registrado para: " + movie.getNome() + " - Total: " + movie.getTotalVotos());
        } else {
            logger.warning("Filme não encontrado com ID: " + movieId);
        }
        return movies;
    }

    @MessageMapping("/listar")
    @SendTo("/topic/movies")
    public Map<String, Movie> listarMovies() {
        logger.info("Listando todos os filmes");
        return movies;
    }
}