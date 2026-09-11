package com.constitution.awareness.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "parts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_part_number",
                        columnNames = "part_number"
                )
        }
)
public class Part {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    @Column(
            name = "part_number",
            nullable = false,
            unique = true,
            length = 50
    )
    private String partNumber;


    @Column(
            nullable = false
    )
    private String title;


    @Column(
            columnDefinition = "TEXT"
    )
    private String description;


    @JsonIgnore
    @OneToMany(
            mappedBy = "part"
    )
    private List<Article> articles =
            new ArrayList<>();


    public Part() {
    }


    public Part(
            String partNumber,
            String title,
            String description
    ) {

        this.partNumber = partNumber;
        this.title = title;
        this.description = description;
    }


    public Long getId() {
        return id;
    }


    public String getPartNumber() {
        return partNumber;
    }


    public void setPartNumber(
            String partNumber
    ) {
        this.partNumber = partNumber;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(
            String title
    ) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(
            String description
    ) {
        this.description = description;
    }


    public List<Article> getArticles() {
        return articles;
    }


    public void setArticles(
            List<Article> articles
    ) {
        this.articles = articles;
    }
}