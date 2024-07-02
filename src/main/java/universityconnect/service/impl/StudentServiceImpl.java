package universityconnect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import universityconnect.domain.*;
import universityconnect.dto.ProfileDTO;
import universityconnect.dto.StudentDTO;
import universityconnect.exception.ResourceNotFoundException;
import universityconnect.mapper.ProfileMapper;
import universityconnect.mapper.StudentMapper;
import universityconnect.repository.*;
import universityconnect.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(long id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student Not Found with ID: " + id));

        existingStudent.setMajor(studentDTO.getMajor());
        existingStudent.setYear(studentDTO.getYear());
        existingStudent.setUsername(studentDTO.getUsername());
        existingStudent.setAddress(studentDTO.getAddress());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setPassword(studentDTO.getPassword());
        existingStudent.setRole(studentDTO.getRole());

        Student student = studentRepository.save(existingStudent);
        return studentMapper.studentToStudentDTO(student);

    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student Not Found with ID: " + id));
        return studentMapper.studentToStudentDTO(student);
    }

    @Override
    public void deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student Not Found with ID: " + id));
        studentRepository.delete(student);
    }

    @Override
    public ProfileDTO getProfileByStudentId(long studentId) {
        Profile profile = profileRepository.findByUserId(studentId);
        return profileMapper.profileToProfileDTO(profile);
    }

    @Override
    public List<StudentDTO> getStudentsByYear(int year) {
        return studentRepository.findByYear(year).stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major).stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsByYearAndMajor(int year, String major) {
        return studentRepository.findByYearAndMajor(year, major).stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }

}