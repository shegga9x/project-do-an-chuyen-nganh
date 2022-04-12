package code.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.helpers.payload.response.TimeTableResponse;
import code.backend.helpers.payload.subModel.SubScoreModel;
import code.backend.persitence.entities.Course;
import code.backend.persitence.entities.CourseOffering;
import code.backend.persitence.entities.FinalResult;
import code.backend.persitence.entities.Schedule;
import code.backend.persitence.entities.SemesterResult;
import code.backend.persitence.entities.Student;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.SubPass;
import code.backend.persitence.model.UserDetailCustom;
import code.backend.persitence.repository.CourseOfferingRepository;
import code.backend.persitence.repository.ScheduleRepository;
import code.backend.persitence.repository.SemesterRepository;
import code.backend.persitence.repository.StudentRepository;
import code.backend.persitence.repository.StudentScheduleFRepository;
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
	@Autowired
	StudentScheduleFRepository studentScheduleFRepository;
	@Autowired
	StudentRepository studentRepository;

	@Test
	@Transactional
	void test1() {
		List<String> listParam = Arrays.asList("18130005", "2021_1");

		List<String[]> columns = entityService.getFunctionResult("get_Semester_Reuslt", listParam);

		List<SemesterReusltDTO> listResult = new ArrayList<>();
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

	@Test
	@Transactional
	void test5() {
		List<String> listParam = Arrays.asList("18130005");
		List<String[]> columns = entityService.getFunctionResult("Time_Table_St", listParam);
		List<TimeTableResponse> listResult = new ArrayList<>();
		// for (String[] arr : columns) {
		// // listResult.add(new TimeTableDTO(arr[0], arr[1]));
		// }
		System.out.println("TimeTable_ST:");
		listResult.forEach(System.out::println);
	}

	@Test
	@Transactional
	void test4() {
		String idSchedule = "57";
		Schedule schedule = scheduleRepository.findById(idSchedule).get();
		List<Student> students = schedule.getListOfStudentSchedule().stream().map(StudentSchedule::getStudent)
				.collect(Collectors.toList());
		System.out.println("Schedule:");
		students.forEach(System.out::println);
	}

	@Test
	@Transactional
	void test6() {
		String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
		List<String> listIdSchedule = studentScheduleFRepository.findIdScheduleByIdSemesterAndIdStudent(semesterID,
				"18130077");
		for (String s : listIdSchedule) {
			System.out.println(s);
		}
	}

	@Test
	@Transactional
	void test7() {
		List<SubScoreModel> subScoreModels = new ArrayList<>();
		String idSemester = "2021_2";
		String idCourseOffering = "52";
		boolean is4Max = false;
		subScoreModels.add(new SubScoreModel("18130005", "", "", 8.0));
		subScoreModels.add(new SubScoreModel("18130006", "", "", 7.0));
		subScoreModels.add(new SubScoreModel("18130007", "", "", 8.0));
		subScoreModels.add(new SubScoreModel("18130007", "", "", 9.0));

		List<String> ids = subScoreModels.stream().map(SubScoreModel::getStudentID)
				.collect(Collectors.toList());
		List<String> idsAfter = ids.stream()
				.filter(e -> Collections.frequency(ids, e) == 1)
				.collect(Collectors.toList());

		List<Student> students = new ArrayList<>();
		for (Student student : studentRepository.findAllById(idsAfter)) {

			List<StudentSchedule> studentSchedules = student.getListOfStudentSchedule().stream()
					.filter(x -> x.getSchedule().getIdCourseOffering().equals(idCourseOffering)
							&& x.getIdSemester().equals(idSemester))
					.collect(Collectors.toList());

			if (studentSchedules.size() != 0) {

				double currentScore = subScoreModels.stream()
						.filter(x -> x.getStudentID().equals(student.getIdStudent())).map(SubScoreModel::getFinalResult)
						.collect(Collectors.toList()).get(0);
				int finalCourseCertificate = 0;
				double finalScore = 0;
				int semesterCourseCertificate = 0;
				double semesterScore = 0;
				boolean studyAgain = false;
				Course currentCourse = studentSchedules.get(0).getSchedule().getCourseOffering().getCourse();

				List<SubPass> subPasses = student.getListOfSubPass();
				for (SubPass subPass : subPasses) {
					int currentCourseCertificate = currentCourse.getCourseCertificate();
					finalCourseCertificate += currentCourseCertificate;
					if (subPass.getIdCourse().equals(currentCourse.getIdCourse())) {
						System.out.println("okoko");
						studyAgain = true;
						finalScore += currentScore > subPass.getScore() ? currentScore
								: subPass.getScore() * currentCourseCertificate;
						if (subPass.getIdSemester().equals(idSemester)) {
							semesterScore += currentScore > subPass.getScore() ? currentScore
									: subPass.getScore() * currentCourseCertificate;
						}
					} else {
						finalScore += subPass.getScore() * currentCourseCertificate;
						if (subPass.getIdSemester().equals(idSemester)) {
							semesterScore += subPass.getScore() * currentCourseCertificate;
						}
					}
				}
				if (!studyAgain) {
					subPasses.add(
							new SubPass(idSemester, currentCourse.getIdCourse(), student.getIdStudent(),
									is4Max ? currentScore * 2.5 : currentScore));
					finalScore += currentScore;
					semesterScore += currentScore;
					finalCourseCertificate += currentCourse.getCourseCertificate();
					semesterCourseCertificate += currentCourse.getCourseCertificate();
				}
				semesterScore = semesterScore / semesterCourseCertificate;
				SemesterResult semesterResult = new SemesterResult(idSemester, student.getIdStudent(), semesterScore,
						semesterCourseCertificate);
				List<SemesterResult> semesterResults = student.getListOfSemesterResult();
				semesterResults.removeIf(x -> x.getIdSemester().equals(idSemester));
				semesterResults.add(semesterResult);
				finalScore = finalScore / finalCourseCertificate;
				FinalResult finalResult = new FinalResult(student.getIdStudent(), finalScore);
				student.setListOfSubPass(subPasses);
				student.setFinalResult(finalResult);
				student.setListOfSemesterResult(semesterResults);
				System.out.println(Arrays.toString(subPasses.toArray()));
				students.add(student);
			}
		}
		studentRepository.saveAll(students);
	}

	@Test
	void test8() {
		List<String> idsError = new ArrayList<>();
		Map<String, String> map = new HashMap<String, String>();
		for (String i : idsError)
			map.put(i, "Bị trùng ID trong excel");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue().toString());
		}
	}

}
