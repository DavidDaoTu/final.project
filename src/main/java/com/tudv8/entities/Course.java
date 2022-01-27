package com.tudv8.entities;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
