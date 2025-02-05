package com.example.user_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String title;
    private String description;
    private Long companyId;

    public Review() {
    }

    public Review(Long id, String title, String description, Long companyId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.companyId = companyId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
