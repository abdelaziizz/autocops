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
    private FileFormat import_File_format;

    @ManyToOne
    @JoinColumn(name = "export_format_id")
    private FileFormat export_File_format;

    @Column(name = "fail_on_error")
    private boolean fail_on_error;

    @Column(name = "active")
    private boolean active;

    @Column(name = "reading_line")
    private Integer reading_line;

    @OneToOne
    @JoinColumn(name = "service")
    private ServiceEntity service;

    @Column(name = "import_path")
    private String import_path;

    @Column(name = "export_path")
    private String export_path;

    @Column(name = "template_path")
    private String template_path;

    @Column(name = "reading_root")
    private String reading_root;

    @Column(name = "writing_root")
    private String writing_root;
}
