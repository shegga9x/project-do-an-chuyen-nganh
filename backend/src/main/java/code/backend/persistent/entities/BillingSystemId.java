/*
 * Created on 2022-03-22 ( 22:01:18 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persistent.entities;

import java.io.Serializable;


/**
 * Composite primary key for entity "BillingSystem" ( stored in table "Billing_System" )
 *
 * @author Telosys
 *
 */
public class BillingSystemId implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private String     idSemester ;
    
    private String     idStudent ;
    
    /**
     * Constructor
     */
    public BillingSystemId() {
        super();
    }

    /**
     * Constructor with values
     * @param idSemester 
     * @param idStudent 
     */
    public BillingSystemId( String idSemester, String idStudent ) {
        super();
        this.idSemester = idSemester ;
        this.idStudent = idStudent ;
    }
    
    //--- GETTERS & SETTERS FOR KEY FIELDS
    public void setIdSemester( String value ) {
        this.idSemester = value;
    }
    public String getIdSemester() {
        return this.idSemester;
    }

    public void setIdStudent( String value ) {
        this.idStudent = value;
    }
    public String getIdStudent() {
        return this.idStudent;
    }


    //--- equals METHOD
	@Override
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		BillingSystemId other = (BillingSystemId) obj; 
		//--- Attribute idSemester
		if ( idSemester == null ) { 
			if ( other.idSemester != null ) 
				return false ; 
		} else if ( ! idSemester.equals(other.idSemester) ) 
			return false ; 
		//--- Attribute idStudent
		if ( idStudent == null ) { 
			if ( other.idStudent != null ) 
				return false ; 
		} else if ( ! idStudent.equals(other.idStudent) ) 
			return false ; 
		return true; 
	} 

    //--- hashCode METHOD
	@Override
	public int hashCode() { 
		final int prime = 31; 
		int result = 1; 
		
		//--- Attribute idSemester
		result = prime * result + ((idSemester == null) ? 0 : idSemester.hashCode() ) ; 
		//--- Attribute idStudent
		result = prime * result + ((idStudent == null) ? 0 : idStudent.hashCode() ) ; 
		
		return result; 
	} 

    //--- toString METHOD
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idSemester);
        sb.append("|");
        sb.append(idStudent);
        return sb.toString(); 
    } 

}
