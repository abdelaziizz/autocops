package com.mdp.autocops.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "formats")
public class FileFormat {

    @Id
    @Column(name = "format_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long format_id;

    @Column(name = "format_type")
    private String format_type;
}
