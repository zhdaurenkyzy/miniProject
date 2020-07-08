package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ARTICLE")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "TITLE")
    @Size(max = 100, message = "Title must contain max of 100 symbols")
    private String title;

    @Column(name = "ARTICLE_TEXT")
    @Size(max = 400, message = "Title must contain max of 100 symbols")
    private String text;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "ARTICLE_USER_ID")
    @NotNull
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.ALL})
    private List<Comment> list;
}
