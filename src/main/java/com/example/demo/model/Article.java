package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String title;
    @Column(name = "ARTICLE_TEXT")
    private String text;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "ARTICLE_USER_ID")
    //  @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.ALL})
    private List<Comment> list;
}
