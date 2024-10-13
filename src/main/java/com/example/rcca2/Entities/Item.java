package com.example.rcca2.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid = 0L;

    private String contributor;

    private Long uid;  //published user ID

    private String author;

    private String email;

    private String title;

    private String description;

    private String source;

    private String make;  //Toyota, Honda, etc.

    private String model;  //Camry, Accord, etc.

    private String year;  //2019, 2020, etc.

    private String category;  //Sedan, SUV, etc.

    private String engine;  //2.5L, 3.5L, hybrid etc.

    private String file_format;

    private Date publish_date;

    private int status = 0;

    private String sourceType;

    private String approved_date;

    private Date last_updated_date;

    private Long approved_by;

    private String file_name;

    private String file_path;

    public Item(String creator, String title, String description) {
        this.author = creator;
        this.title = title;
        this.description = description;
    }
}





