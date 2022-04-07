package code.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.persitence.entities.CourseOffering;
import code.backend.persitence.entities.Schedule;
import code.backend.persitence.entities.Semester;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.repository.ScheduleRepository;
import code.backend.service.subService.EntityService;

@RunWith(SpringRunner.class)

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private EntityService entityService;
	@Autowired
	ScheduleRepository scheduleRepository;


	@Test
	@Transactional
	void test1() {
		List<String> listParam = Arrays.asList("18130005", "2021_1");

		List<String[]> columns = entityService.getFunctionResult("get_Semester_Reuslt", listParam);

		List<SemesterReusltDTO> listResult = new ArrayList<>() {
		};
		for (String[] arr : columns) {
			listResult.add(new SemesterReusltDTO(arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),
					Double.parseDouble(arr[4])));
		}
		System.out.println(listResult);

	}

	@Test
	@Transactional
	void test2() {
		List<String> ids = new ArrayList<>();
		for (String[] strings : entityService.getFunctionResult("Sub_Available_ST",
				Arrays.asList("18130005"))) {
			ids.add(strings[0]);
		}

		for (Schedule schedule : scheduleRepository.findAllByIds(ids)) {
			System.out.println(schedule);
		}
	}
	@Test
	@Transactional
	void test3() {
		// courseOfferingRepository
		// studentScheduleRepository
		// semesterRepository
		// Schedule schedule =  scheduleRepository.findById("find by semesterRepository");
		// //Course_Offering
		// CourseOffering courseOffering = courseOfferingRepository.findById(schedule.getIdCourseOffering());
		// courseOffering.setCurrentSize(courseOffering.getCurrentSize() +1);
		// courseOfferingRepository.save(courseOffering);
		// //StudentSchedule
		// StudentSchedule studentSchedule = new StudentSchedule("find by semesterRepository", schedule.getIdSchedule(), "from httpcontext");
		// studentScheduleRepository.save(studentSchedule);
	}
}
