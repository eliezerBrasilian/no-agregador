package com.cryxie.controller;

import static com.cryxie.utils.AppUtils.movieEndpoint;

import java.io.IOException;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cryxie.data.RespostaBase;
import com.cryxie.data.dtos.responses.MovieResponseDto;
import com.cryxie.models.Movie;

@RestController
@RequestMapping(movieEndpoint)
public class MovieController {

    @Repository
    public interface MovieRepository extends MongoRepository<Movie, String> {
        long count();
    }

    @GetMapping()
    ResponseEntity<RespostaBase<List<MovieResponseDto>>> getAll() {
        return null;
    }
}
