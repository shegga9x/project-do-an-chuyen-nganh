/*
 * Created on 2022-03-29 ( 21:59:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;
import java.io.Serializable;


/**
 * Composite primary key for entity "StudentSchedule" ( stored in table "Student_Schedule" )
 *
 * @author Telosys
 *
 */
public class StudentScheduleId implements Serializable {    

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private String     idSemester ;
    
    private String     idSchedule ;
    
    private String     idStudent ;
    
    /**
     * Constructor
     */
    public StudentScheduleId() {
        super();
    }

    /**
     * Constructor with values
     * @param idSemester 
     * @param idSchedule 
     * @param idStudent 
     */
    public StudentScheduleId( String idSemester, String idSchedule, String idStudent ) {
        super();
        this.idSemester = idSemester ;
        this.idSchedule = idSchedule ;
        this.idStudent = idStudent ;
    }
    
    //--- GETTERS & SETTERS FOR KEY FIELDS
    public void setIdSemester( String value ) {
        this.idSemester = value;
    }
    public String getIdSemester() {
        return this.idSemester;
    }

    public void setIdSchedule( String value ) {
        this.idSchedule = value;
    }
    public String getIdSchedule() {
        return this.idSchedule;
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
		StudentScheduleId other = (StudentScheduleId) obj; 
		//--- Attribute idSemester
		if ( idSemester == null ) { 
			if ( other.idSemester != null ) 
				return false ; 
		} else if ( ! idSemester.equals(other.idSemester) ) 
			return false ; 
		//--- Attribute idSchedule
		if ( idSchedule == null ) { 
			if ( other.idSchedule != null ) 
				return false ; 
		} else if ( ! idSchedule.equals(other.idSchedule) ) 
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
		//--- Attribute idSchedule
		result = prime * result + ((idSchedule == null) ? 0 : idSchedule.hashCode() ) ; 
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
        sb.append(idSchedule);
        sb.append("|");
        sb.append(idStudent);
        return sb.toString(); 
    } 

}
