package com.cryxie.controller;

import static com.cryxie.utils.AppUtils.votoEndpoint;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cryxie.data.RespostaBase;
import com.cryxie.data.dtos.responses.MovieResponseDto;

@RestController
@RequestMapping(votoEndpoint)
public class VotosControlle {

    @GetMapping()
    ResponseEntity<RespostaBase<List<MovieResponseDto>>> getAll() {
        return null;
    }

}
