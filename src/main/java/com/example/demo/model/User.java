package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "NAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob
    @Column(name = "FILE")
    private String file;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
  //  @JsonManagedReference
    List<Article> articleList;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL})
    //@JsonManagedReference
    List<Comment> commentList;
}
