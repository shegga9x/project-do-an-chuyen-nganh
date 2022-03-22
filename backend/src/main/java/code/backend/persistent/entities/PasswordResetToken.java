/*
 * Created on 2022-03-22 ( 22:26:35 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persistent.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * JPA entity class for "PasswordResetToken"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="password_reset_token", schema="dbo", catalog="Course_Registration" )
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="ID_ACCOUNT", nullable=false, length=50)
    private String     idAccount ;

    //--- ENTITY DATA FIELDS 
    @Column(name="reset_token_content", length=2147483647)
    private String     resetTokenContent ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="reset_token_expires")
    private Date       resetTokenExpires ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="password_reset")
    private Date       passwordReset ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToOne
    @JoinColumn(name="ID_ACCOUNT", referencedColumnName="ID_ACCOUNT", insertable=false, updatable=false)
    private Account    account ; 


    /**
     * Constructor
     */
    public PasswordResetToken() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setIdAccount( String idAccount ) {
        this.idAccount = idAccount ;
    }
    public String getIdAccount() {
        return this.idAccount;
    }

    public void setResetTokenContent( String resetTokenContent ) {
        this.resetTokenContent = resetTokenContent ;
    }
    public String getResetTokenContent() {
        return this.resetTokenContent;
    }

    public void setResetTokenExpires( Date resetTokenExpires ) {
        this.resetTokenExpires = resetTokenExpires ;
    }
    public Date getResetTokenExpires() {
        return this.resetTokenExpires;
    }

    public void setPasswordReset( Date passwordReset ) {
        this.passwordReset = passwordReset ;
    }
    public Date getPasswordReset() {
        return this.passwordReset;
    }

    //--- GETTERS FOR LINKS
    public Account getAccount() {
        return this.account;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idAccount);
        sb.append("|");
        sb.append(resetTokenContent);
        sb.append("|");
        sb.append(resetTokenExpires);
        sb.append("|");
        sb.append(passwordReset);
        return sb.toString(); 
    } 

}
