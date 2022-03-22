/*
 * Created on 2022-03-22 ( 22:26:36 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persistent.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * JPA entity class for "RefreshToken"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="refresh_token", schema="dbo", catalog="Course_Registration" )
public class RefreshToken implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Integer    id ;

    //--- ENTITY DATA FIELDS 
    @Column(name="token", length=2147483647)
    private String     token ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="expires", nullable=false)
    private Date       expires ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created", nullable=false)
    private Date       created ;

    @Column(name="created_by_ip", length=2147483647)
    private String     createdByIp ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="revoked")
    private Date       revoked ;

    @Column(name="revoked_by_ip", length=2147483647)
    private String     revokedByIp ;

    @Column(name="replaced_by_token", length=2147483647)
    private String     replacedByToken ;

    @Column(name="reason_revoked", length=2147483647)
    private String     reasonRevoked ;

    @Column(name="account_id", nullable=false, length=50)
    private String     accountId ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="account_id", referencedColumnName="ID_ACCOUNT", insertable=false, updatable=false)
    private Account    account ; 


    /**
     * Constructor
     */
    public RefreshToken() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setId( Integer id ) {
        this.id = id ;
    }
    public Integer getId() {
        return this.id;
    }

    public void setToken( String token ) {
        this.token = token ;
    }
    public String getToken() {
        return this.token;
    }

    public void setExpires( Date expires ) {
        this.expires = expires ;
    }
    public Date getExpires() {
        return this.expires;
    }

    public void setCreated( Date created ) {
        this.created = created ;
    }
    public Date getCreated() {
        return this.created;
    }

    public void setCreatedByIp( String createdByIp ) {
        this.createdByIp = createdByIp ;
    }
    public String getCreatedByIp() {
        return this.createdByIp;
    }

    public void setRevoked( Date revoked ) {
        this.revoked = revoked ;
    }
    public Date getRevoked() {
        return this.revoked;
    }

    public void setRevokedByIp( String revokedByIp ) {
        this.revokedByIp = revokedByIp ;
    }
    public String getRevokedByIp() {
        return this.revokedByIp;
    }

    public void setReplacedByToken( String replacedByToken ) {
        this.replacedByToken = replacedByToken ;
    }
    public String getReplacedByToken() {
        return this.replacedByToken;
    }

    public void setReasonRevoked( String reasonRevoked ) {
        this.reasonRevoked = reasonRevoked ;
    }
    public String getReasonRevoked() {
        return this.reasonRevoked;
    }

    public void setAccountId( String accountId ) {
        this.accountId = accountId ;
    }
    public String getAccountId() {
        return this.accountId;
    }

    //--- GETTERS FOR LINKS
    public Account getAccount() {
        return this.account;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(id);
        sb.append("|");
        sb.append(token);
        sb.append("|");
        sb.append(expires);
        sb.append("|");
        sb.append(created);
        sb.append("|");
        sb.append(createdByIp);
        sb.append("|");
        sb.append(revoked);
        sb.append("|");
        sb.append(revokedByIp);
        sb.append("|");
        sb.append(replacedByToken);
        sb.append("|");
        sb.append(reasonRevoked);
        sb.append("|");
        sb.append(accountId);
        return sb.toString(); 
    } 

}
