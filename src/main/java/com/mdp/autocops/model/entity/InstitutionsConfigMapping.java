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

    @Column(name = "import_field_type")
    private String import_field_type;

    @OneToOne
    @JoinColumn(name = "export_field_head")
    private ExportField export_field_head;

    @OneToOne
    @JoinColumn(name = "import_field")
    private ImportField import_field;

    @Column(name = "start_index")
    private int start_index;

    @Column(name = "last_index")
    private int last_index;

    @Column(name = "required")
    private boolean required;

}
