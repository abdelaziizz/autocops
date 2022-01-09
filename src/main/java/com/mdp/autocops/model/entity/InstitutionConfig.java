package com.mdp.autocops.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "institutions_config")
public class InstitutionConfig {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "inst_id")
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "import_format_id")
    private Format import_format;

    @ManyToOne
    @JoinColumn(name = "export_format_id")
    private Format export_format;

    @Column(name = "fail_on_error")
    private boolean fail_on_error;

    @Column(name = "active")
    private boolean active;

    @OneToOne
    @JoinColumn(name = "service")
    private ServiceEntity service;
}
