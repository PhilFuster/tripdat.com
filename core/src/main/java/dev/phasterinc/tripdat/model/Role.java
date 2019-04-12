package dev.phasterinc.tripdat.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @SequenceGenerator(name = "role_generator", sequenceName = "role_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "role_generator")
    @Column(name = "role_id", columnDefinition = "BIGINT")
    private Long roleId;

    @Column(name = "role_name", columnDefinition = "TEXT")
    private String name;

    public Role(String role_name) {
        name = role_name;
    }

    public Role() {

    }



}
