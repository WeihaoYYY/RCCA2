package com.example.rcca2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailsDTO {

    private Long sid;
    private String approvedDate;
    private String author;
    private String category;
    private String contributor;
    private String description;
    private String email;
    private String engine;
    private String fileFormat;
    private String filePath;
    private String lastUpdatedDate;
    private String make;
    private String model;
    private String publishDate;
    private String source;
    private int status;
    private String title;
    private String uid;
    private String year;


}
