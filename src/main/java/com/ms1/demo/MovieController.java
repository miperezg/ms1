package com.ms1.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MovieController {

    private List<Movie> movies = new ArrayList<Movie>();

    @Autowired
    private final ObjectMapper objectMapper;

    private void LoadMoviesJson() throws IOException, StreamReadException, DatabindException{
        ClassPathResource resource = new ClassPathResource("static/movies.json");
        File file = resource.getFile();
        movies = objectMapper.readValue(file, new TypeReference<List<Movie>>(){});
    }

    public MovieController() throws StreamReadException, DatabindException, IOException{
        this.objectMapper = new ObjectMapper();
        try{
            LoadMoviesJson();
        }
        catch(Exception e){
            log.info(e.getMessage());
        }
    }

    @GetMapping("/peliculas")
    public ResponseEntity<List<Movie>> getMovies(){
        if (movies.size() < 1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);

    }

    @GetMapping("/peliculas/{id}")
    public ResponseEntity<Movie> getmovie(
        @PathVariable("id") Integer id
    ){
        var stream = movies.stream();
        var filtered =  stream.filter(movie -> movie.getId().equals(id));
        var found = filtered.findFirst().orElse(null);
        if (Objects.nonNull(found)){
            return new ResponseEntity<>(found ,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
