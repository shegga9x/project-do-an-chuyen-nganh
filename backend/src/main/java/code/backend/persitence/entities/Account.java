/*
 * Created on 2022-03-29 ( 21:59:26 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Value;

/**
 * JPA entity class for "Account"
 *
 * @author Telosys
 *
 */
@Entity

@Table(name = "ACCOUNT", schema = "dbo", catalog = "${bezkoder.app.databaseName}")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- ENTITY PRIMARY KEY
    @Id
    @Column(name = "ID_ACCOUNT", nullable = false, length = 50)
    private String idAccount;

    // --- ENTITY DATA FIELDS
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = true)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_expires", nullable = true)
    private Date lastExpires;

    @Column(name = "accept_terms", nullable = false)
    private Boolean acceptTerms;

    // --- ENTITY LINKS ( RELATIONSHIP )

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Student student;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<RefreshToken> listOfRefreshToken;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private ResetToken resetToken;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private VerificationToken verificationToken;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Professor professor;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private AccountDetail accountDetail;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ACCOUNT_has_role", joinColumns = @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT"), inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
    private List<Role> listOfRole;

    /**
     * Constructor
     */
    public Account() {
        super();
    }

    // --- GETTERS & SETTERS FOR FIELDS
    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdAccount() {
        return this.idAccount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setLastExpires(Date lastExpires) {
        this.lastExpires = lastExpires;
    }

    public Date getLastExpires() {
        return this.lastExpires;
    }

    public void setAcceptTerms(Boolean acceptTerms) {
        this.acceptTerms = acceptTerms;
    }

    public Boolean getAcceptTerms() {
        return this.acceptTerms;
    }

    // --- GETTERS FOR LINKS

    public Student getStudent() {
        return this.student;
    }

    public List<RefreshToken> getListOfRefreshToken() {
        return this.listOfRefreshToken;
    }

    public void addToListOfRefreshToken(RefreshToken refreshToken) {
        this.listOfRefreshToken.add(refreshToken);
        setListOfRefreshToken(this.listOfRefreshToken);
    }

    public ResetToken getResetToken() {
        return this.resetToken;
    }

    public VerificationToken getVerificationToken() {
        return this.verificationToken;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public AccountDetail getAccountDetail() {
        return this.accountDetail;
    }

    public List<Role> getListOfRole() {
        return this.listOfRole;
    }

    // --- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(idAccount);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(passwordHash);
        sb.append("|");
        sb.append(created);
        sb.append("|");
        sb.append(updated);
        sb.append("|");
        sb.append(lastExpires);
        sb.append("|");
        sb.append(acceptTerms);
        return sb.toString();
    }

    public void setListOfRefreshToken(List<RefreshToken> listOfRefreshToken) {
        this.listOfRefreshToken = listOfRefreshToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void setResetToken(ResetToken resetToken) {
        this.resetToken = resetToken;
    }

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public void setListOfRole(List<Role> listOfRole) {
        this.listOfRole = listOfRole;
    }

    // getter setter for respone or request

    public String getFirstName() {
        return this.getAccountDetail().getFirstName();
    }

    public void setFirstName(String firstName) {
        this.getAccountDetail().setFirstName(firstName);
    }

    public String getLastName() {
        return this.getAccountDetail().getLastName();
    }

    public void setLastName(String lastName) {
        this.getAccountDetail().setLastName(lastName);
    }

}
