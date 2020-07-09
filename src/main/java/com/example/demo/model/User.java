package com.example.demo.model;

import com.example.demo.validation.CustomValidPassword;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
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
    @Size(min = 4, message = "UserName must contain min of 4 symbols")
    private String userName;

    @Column(name = "EMAIL")
    @Email
    private String email;

    @Column(name = "PASSWORD")
    @CustomValidPassword
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob
    @Column(name = "FILE")
    private byte[] file;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private List<Article> articleList;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private List<Comment> commentList;
}
