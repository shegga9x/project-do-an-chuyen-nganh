package code.backend.service.subService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class EntityService {
    @Autowired
    private EntityManager entityManager;

    public List<String[]> getFunctionResult(String functionName, List<String> listParam) {
        String params = "";
        for (int i = 0; i < listParam.size(); i++) {
            params += ":" + i + ",";
        }
        params = params.substring(0, params.length() - 1);
        String queryContent = MessageFormat.format("SELECT * FROM {0}({1})", functionName, params);
        Query query = entityManager.createNativeQuery(queryContent);
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i + "", listParam.get(i));
        }
        List<Object[]> list = query.getResultList();
        List<String[]> result = new ArrayList<>();
        try {
            for (Object[] strings : list) {
                String[] stringsToChar = new String[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    stringsToChar[i] = strings[i].toString();
                }
                result.add(stringsToChar);
            }
        } catch (Exception e) {
            String resultSplit = list.toString().substring(1, list.toString().length() - 1);
            List<String> listAfterConvert = new ArrayList<String>(Arrays.asList(resultSplit.split(", ")));
            for (String string : listAfterConvert) {
                result.add(new String[] { string });
            }
        }
        return result;

    }
}
