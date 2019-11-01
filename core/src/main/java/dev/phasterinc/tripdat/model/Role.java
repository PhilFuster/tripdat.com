package dev.phasterinc.tripdat.model;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Name: Role
 * Purpose: Used for implementing Role based security and authentication in TripdatTrip.
 */

@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {

    @Id
    @SequenceGenerator(name = "role_generator", sequenceName = "role_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @Column(name = "role_id", columnDefinition = "BIGINT")
    private Long roleId;


    @Column(name = "role_name", columnDefinition = "TEXT")
    @NaturalId
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
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

    /**
     * Name: equals
     * Purpose: Overriding the default implementation of equals method to ensure validity of comparison
     * Synopsis: When using database-generated unique identifiers, a comparison between the two objects' id
     * is what is needed.
     * <p>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return roleId != null && roleId.equals(((Role) o).getRoleId());
    }

    /**
     * Name: hashCode
     * Purpose: Overrides default implementation of hashCode
     * Synopsis: When using database-generated unique identifiers, the hashcode needs to
     * be consistent across state transitions, thus the reason for returing 31.
     * <p>
     */
    @Override
    public int hashCode() {
        return 31;
    }

    /**
     * Name: toString
     * Purpose: Overrides the default toString implemenation.
     * <p>
     */
    @Override
    public String toString() {
        return "[RoleId: " + roleId + ", RoleName: " + name + "]";
    }


}
