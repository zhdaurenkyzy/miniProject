package com.example.demo.model;

import com.example.demo.validation.CustomValidPassword;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @ApiModelProperty(notes = "The database generated user ID")
    private Long id;

    @Column(name = "NAME")
    @Size(min = 4, message = "UserName must contain min of 4 symbols")
    @ApiModelProperty(notes = "The user's name")
    private String userName;

    @Column(name = "EMAIL")
    @Email
    @ApiModelProperty(notes = "The user's email")
    private String email;

    @Column(name = "PASSWORD")
    @CustomValidPassword
    @ApiModelProperty(notes = "The user's password")
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "The user's authority")
    private Role role;

    @Lob
    @Column(name = "FILE")
    @ApiModelProperty(notes = "The user's CV")
    private String file;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonManagedReference
    @ApiModelProperty(notes = "Article List")
    private List<Article> articleList;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL})
    @JsonManagedReference
    @ApiModelProperty(notes = "Comments of user")
    private List<Comment> commentList;
}
