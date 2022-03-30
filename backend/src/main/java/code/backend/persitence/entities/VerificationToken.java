/*
 * Created on 2022-03-29 ( 21:59:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * JPA entity class for "VerificationToken"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name = "verification_token", schema = "dbo", catalog = "Course_Registration")
public class VerificationToken implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- ENTITY PRIMARY KEY
    @Id
    @Column(name = "ID_ACCOUNT", nullable = false, length = 50)
    private String idAccount;

    // --- ENTITY DATA FIELDS
    @Column(name = "verification_token_content", length = 2147483647)
    private String verificationTokenContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "verified")
    private Date verified;

    // --- ENTITY LINKS ( RELATIONSHIP )
    @OneToOne
    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT", insertable = false, updatable = false)
    private Account account;

    /**
     * Constructor
     */
    public VerificationToken() {
        super();
    }

    public VerificationToken(String idAccount, String verificationTokenContent) {
        this.idAccount = idAccount;
        this.verificationTokenContent = verificationTokenContent;
    }

    // --- GETTERS & SETTERS FOR FIELDS
    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdAccount() {
        return this.idAccount;
    }

    public void setVerificationTokenContent(String verificationTokenContent) {
        this.verificationTokenContent = verificationTokenContent;
    }

    public String getVerificationTokenContent() {
        return this.verificationTokenContent;
    }

    public void setVerified(Date verified) {
        this.verified = verified;
    }

    public Date getVerified() {
        return this.verified;
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
        sb.append(verificationTokenContent);
        sb.append("|");
        sb.append(verified);
        return sb.toString();
    }

}