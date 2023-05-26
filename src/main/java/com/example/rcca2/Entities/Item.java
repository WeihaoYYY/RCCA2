package com.example.rcca2.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid = 0L;

    private String contributor;

    private String creator;

    private String creator_email;

    private String title;

    private String description;

    private String source;

    private String car_model;

    private String file_format;

    private String subject;

    private String identifier;

    private String language;

    private String publisher;

    private String relation;

    private String rights;

    private String type;

    private String publish_date;

    private int status = 0;

    private String approved_date;

    private String last_updated_date;

    private String file_path;

    public Item(String creator, String title, String description) {
        this.creator = creator;
        this.title = title;
        this.description = description;
    }
}





