package com.constitution.awareness.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {


    /*
     * =====================================
     * PRIMARY KEY
     * =====================================
     */

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    /*
     * =====================================
     * USER INFORMATION
     * =====================================
     */

    @Column(
            nullable = false,
            length = 100
    )
    private String name;


    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;


    @Column(
            nullable = false
    )
    private String password;


    /*
     * =====================================
     * ROLE
     * =====================================
     */

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            nullable = false,
            length = 20
    )
    private Role role;


    /*
     * =====================================
     * ACCOUNT STATUS
     * =====================================
     */

    @Column(
            nullable = false
    )
    private boolean active = true;


    /*
     * =====================================
     * CONSTRUCTORS
     * =====================================
     */

    public User() {
    }


    public User(

            String name,

            String email,

            String password,

            Role role

    ) {

        this.name =
                name;

        this.email =
                email;

        this.password =
                password;

        this.role =
                role;

        this.active =
                true;
    }


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public Role getRole() {
        return role;
    }


    public boolean isActive() {
        return active;
    }


    /*
     * =====================================
     * SETTERS
     * =====================================
     */

    public void setName(
            String name
    ) {

        this.name =
                name;
    }


    public void setEmail(
            String email
    ) {

        this.email =
                email;
    }


    public void setPassword(
            String password
    ) {

        this.password =
                password;
    }


    public void setRole(
            Role role
    ) {

        this.role =
                role;
    }


    public void setActive(
            boolean active
    ) {

        this.active =
                active;
    }
}