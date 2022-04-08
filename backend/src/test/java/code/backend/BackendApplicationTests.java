package code.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.persitence.entities.CourseOffering;
import code.backend.persitence.entities.Schedule;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.model.UserDetailCustom;
import code.backend.persitence.repository.CourseOfferingRepository;
import code.backend.persitence.repository.ScheduleRepository;
import code.backend.persitence.repository.SemesterRepository;
import code.backend.persitence.repository.StudentScheduleRepository;
import code.backend.service.subService.EntityService;

@RunWith(SpringRunner.class)

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private EntityService entityService;
	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	private CourseOfferingRepository courseOfferingRepository;
	@Autowired
	SemesterRepository semesterRepository;
	@Autowired
	StudentScheduleRepository studentScheduleRepository;

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

		Schedule schedule = scheduleRepository.findById("48").get();

		CourseOffering courseOffering = courseOfferingRepository.findById(schedule.getIdCourseOffering()).get();
		courseOffering.setCurrentSize((byte) (courseOffering.getCurrentSize() + 1));
		courseOfferingRepository.save(courseOffering);
		String id = "";
		try {
			UserDetailCustom currentAccount = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			id = currentAccount.getId();
		} catch (Exception e) {
			id = "18130005";
		}

		StudentSchedule studentSchedule = new StudentSchedule(semesterRepository.getCurrentSemester().getIdSemester(),
				schedule.getIdSchedule(), id);
		studentScheduleRepository.save(studentSchedule);
		System.out.println(studentScheduleRepository.save(studentSchedule));

	}
}
