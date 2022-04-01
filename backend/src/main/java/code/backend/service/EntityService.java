package code.backend.service;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
    @Autowired
    private EntityManager entityManager;

    public List<?> getFunction(String functionName, List<String> listParam, List<?> list) {
        String params = "";
        for (int i = 0; i < listParam.size(); i++) {
            params += ":" + i + ",";
        }
        params = params.substring(0, params.length() - 1);
        String queryContent = MessageFormat.format("SELECT * FROM {0}({1})", functionName, params);
        Query query = entityManager.createNativeQuery(queryContent);
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i+"", listParam.get(i));
        }
        list = query.getResultList();
        return list;

    }
}
