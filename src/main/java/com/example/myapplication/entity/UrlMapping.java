package com.example.myapplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "url_mapping")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated primary key used for encoding

    @Column(nullable = false)
    private Long userId; // Provided by client

    @Column(nullable = false)
    private String oldUrl;

    @Column(unique = true, nullable = true)
    private String shortUrl;
}
