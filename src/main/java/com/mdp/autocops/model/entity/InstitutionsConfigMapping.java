package com.mdp.autocops.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "institutions_config_mapping")
public class InstitutionsConfigMapping {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "inst_config_id", referencedColumnName = "id")
    private InstitutionConfig institution_config;

    @Column(name = "import_field")
    private int import_field;

    @Column(name = "export_field")
    private int export_field;

}
