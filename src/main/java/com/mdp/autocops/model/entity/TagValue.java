package com.mdp.autocops.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tag_values")
public class TagValue {

    @Id
    @Column(name = "tag_name")
    private String tag_name;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<DefaultValue> defaultValues = new HashSet<>();


    public void addDefaultValue(DefaultValue defaultValue){
        defaultValues.add(defaultValue);
    }
}
