package code.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import code.backend.service.EntityService;

@RunWith(SpringRunner.class)
@SuppressWarnings("unchecked")
@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EntityService entityService;

	@Test
	@Transactional
	void contextLoads() {
		// Query query = entityManager.createNativeQuery("SELECT * FROM
		// Sub_Available_ST(:1,:2,:3)");
		// query.setParameter("1", "18130001");
		// List<String[]> columns = query.getResultList();
		// select * from get_Semester_Reuslt(N'18130005','2021_1')
		List<String> listParam = Arrays.asList("18130005", "2021_1");
		List<String[]> columns = new ArrayList<>();
		columns = (List<String[]>) entityService.getFunction("get_Semester_Reuslt", listParam, columns);
//		for (String[] strings : columns) {
//			String[] str = strings.keySet().toArray(new String[strings.size()]);
//
//		}
		System.out.println(columns);

	}

}
