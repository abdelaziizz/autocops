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

    @Column(name = "import_field_index")
    private int import_field_index;

    @OneToOne
    @JoinColumn(name = "import_field_type")
    private FieldType import_field_type;

    @OneToOne
    @JoinColumn(name = "import_field_format")
    private FieldFormat import_field_format;

    @Column(name = "export_field_head")
    private String export_field_head;

}
