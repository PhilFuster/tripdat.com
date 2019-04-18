package dev.phasterinc.tripdat.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {

    @Id
    @SequenceGenerator(name = "role_generator", sequenceName = "role_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "role_generator")
    @Column(name = "role_id", columnDefinition = "BIGINT")
    private Long roleId;


    @Column(name = "role_name", columnDefinition = "TEXT")
    @NaturalId
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<TripdatUser> users = new HashSet<>();


    // == constructors ==
    public Role() {
        this.roleId = Long.parseLong("1");
        this.name = "ROLE_USER";

    }

    public Role(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;

    }

    // == public methods ==
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "[RoleId: " + roleId + ", RoleName: " + name + "]";
    }



}
