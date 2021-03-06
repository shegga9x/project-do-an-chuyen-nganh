package code.backend.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import code.backend.persitence.entities.Clazz;
import code.backend.persitence.repository.ClazzRepository;
import code.backend.persitence.repository.FacultyRepository;

@Service
public class ClazzService {
    @Autowired
    private ClazzRepository clazzRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    public void createClazz(Map<String, Integer> studentCount) {
        for (Map.Entry<String, Integer> entry : studentCount.entrySet()) {
            String idFaculty = entry.getKey();
            int count = entry.getValue();
            if (facultyRepository.findByIdFaculty(idFaculty).isPresent()) {
                createClazzByIdFaculty(idFaculty, count);
            }
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
    }

    public void createClazzByIdFaculty(String idFaculty, int count) {
        List<Clazz> listClazz = new ArrayList<>();
        int countClazz = (int) Math.round((double) count / 100);
        if (countClazz == 0) {
            countClazz = 1;
        }
        for (int i = 0; i < countClazz; i++) {
            char c = (char) (65 + i);
            Clazz clazz = new Clazz();
            int khoa = Calendar.getInstance().get(Calendar.YEAR) % 100;
            String clazzCode = "DH" + khoa + idFaculty + c;
            clazz.setClazzCode(clazzCode);
            clazz.setIdFaculty(idFaculty);
            clazz.setMaxSize((byte) 100);
            clazz.setCurrentSize((byte) 0);
            listClazz.add(clazz);
        }
        clazzRepository.saveAll(listClazz);
    }
}
