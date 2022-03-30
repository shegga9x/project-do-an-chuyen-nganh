/*
 * Created on 2022-03-29 ( 21:59:26 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * JPA entity class for "Role"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name = "Role", schema = "dbo", catalog = "Course_Registration")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- ENTITY PRIMARY KEY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // --- ENTITY DATA FIELDS
    @Column(name = "role_name", nullable = false)
    private RoleEnum roleName;

    // --- ENTITY LINKS ( RELATIONSHIP )
    @ManyToMany(mappedBy = "listOfRole")
    private List<Account> listOfAccount;

    @OneToMany(mappedBy = "role")
    private List<Privilege> listOfPrivilege;

    /**
     * Constructor
     */
    public Role() {
        super();
    }

    public Role(RoleEnum roleEnum) {
        this.roleName = roleEnum;
    }

    // --- GETTERS & SETTERS FOR FIELDS
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setRoleName(RoleEnum roleName) {
        this.roleName = roleName;
    }

    public RoleEnum getRoleName() {
        return this.roleName;
    }

    // --- GETTERS FOR LINKS
    public List<Account> getListOfAccount() {
        return this.listOfAccount;
    }

    public List<Privilege> getListOfPrivilege() {
        return this.listOfPrivilege;
    }

    // --- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append("|");
        sb.append(roleName);
        return sb.toString();
    }

}