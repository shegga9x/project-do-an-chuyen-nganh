package code.backend.service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    EntityManager entityManager;

    public void get_Sub_Available_ST() {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("Sub_Available_ST");
        query.registerStoredProcedureParameter("inparam", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("outparam", String.class, ParameterMode.OUT);

        // set input parameter
        query.setParameter("inparam", "Hello");

        // call the stored procedure and get the result
        query.execute();
        String result = (String) query.getOutputParameterValue("sum");
        System.out.println(result);
    }
}
