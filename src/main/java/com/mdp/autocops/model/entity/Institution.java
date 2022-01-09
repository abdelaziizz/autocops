package com.mdp.autocops.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "institutions")
@NoArgsConstructor
@AllArgsConstructor
public class Institution {


    @Id
    @Column(name = "inst_id")
    private long inst_id;

    @Column(name = "inst_name")
    private String inst_name;


}
