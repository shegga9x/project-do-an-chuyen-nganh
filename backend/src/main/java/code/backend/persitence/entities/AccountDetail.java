/*
 * Created on 2022-03-29 ( 21:59:26 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * JPA entity class for "AccountDetail"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name = "ACCOUNT_detail", schema = "dbo", catalog = "Course_Registration")
public class AccountDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- ENTITY PRIMARY KEY
    @Id
    @Column(name = "ID_ACCOUNT", nullable = false, length = 50)
    private String idAccount;

    // --- ENTITY DATA FIELDS
    @Column(name = "first_name", length = 2147483647, nullable = true)
    private String firstName;

    @Column(name = "last_name", length = 2147483647, nullable = true)
    private String lastName;

    @Column(name = "address", nullable = true, length = 50)
    private String address;

    @Column(name = "phone_number", nullable = true, length = 10)
    private String phoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthday", nullable = true)
    private Date birthday;

    // --- ENTITY LINKS ( RELATIONSHIP )
    @OneToOne
    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT", insertable = false, updatable = false)
    private Account account;

    /**
     * Constructor
     */
    public AccountDetail() {
        super();
    }

    public AccountDetail(String idAccount) {
        this.idAccount = idAccount;

    }

    // --- GETTERS & SETTERS FOR FIELDS
    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdAccount() {
        return this.idAccount;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    // --- GETTERS FOR LINKS
    public Account getAccount() {
        return this.account;
    }

    // --- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(idAccount);
        sb.append("|");
        sb.append(firstName);
        sb.append("|");
        sb.append(lastName);
        sb.append("|");
        sb.append(address);
        sb.append("|");
        sb.append(phoneNumber);
        sb.append("|");
        sb.append(birthday);
        return sb.toString();
    }

}
