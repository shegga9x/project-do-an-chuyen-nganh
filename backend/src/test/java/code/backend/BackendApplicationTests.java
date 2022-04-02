package code.backend;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import code.backend.helpers.payload.testModel.NewModel;
import code.backend.service.EntityService;

@RunWith(SpringRunner.class)

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private EntityService entityService;

	@Test
	@Transactional
	void contextLoads() {
		List<String> listParam = Arrays.asList("18130005", "2021_1");
		List<String[]> columns = entityService.getFunctionResult("get_Semester_Reuslt", listParam);
		for (String[] strings : columns) {
			NewModel model = new NewModel(strings[0], strings[1], Double.parseDouble(strings[2]),
					Double.parseDouble(strings[2]), Double.parseDouble(strings[2]));
			System.out.println(model.toString());
		}
	}
}
