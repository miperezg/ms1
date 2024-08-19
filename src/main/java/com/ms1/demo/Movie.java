package com.ms1.demo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {
    private Integer id;
    private String title;
    private Integer year;
    private String director;
    private String genre;
    private String synopsis;
}
