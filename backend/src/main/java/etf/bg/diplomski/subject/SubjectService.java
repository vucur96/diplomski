package etf.bg.diplomski.subject;

import etf.bg.diplomski.teacher.TeacherToSubjectCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {

  @Autowired private SubjectDTOMapper mapper;

  @Autowired private SubjectRepo subjectRepo;

  public Subject addSubject(SubjectDTO subject) {
    if(subjectRepo.findByName(subject.name()).isPresent()){
      throw new SubjectAlreadyExistsException();
    }
    Subject s = new Subject(subject.name(), subject.status(), subject.description());
    return subjectRepo.save(s);
  }

  public List<SubjectDTO> getAllSubjects() {
    List<SubjectDTO> returnList=new ArrayList<>();
    for (Subject subject :subjectRepo.findAll().stream().toList()) {
        SubjectDTO temp = mapper.apply(subject);
        returnList.add(temp);
    }
    return returnList;
  }

  public void deleteSubject(String name) {
    Optional<Subject> temp=subjectRepo.findByName(name);
    Subject subject;
    if(temp.isPresent()) {
      subject = temp.get();
    }else{
      throw new SubjectNotFoundException();
    }
    subjectRepo.delete(subject);
  }

  public Subject getSubjectByName(String name) {
    if (subjectRepo.findByName(name).isPresent()) {
      return subjectRepo.findByName(name).get();
    }
    throw new SubjectNotFoundException();
  }

  public void acceptSubject(String name) throws SubjectNotFoundException{
    Optional<Subject> temp=subjectRepo.findByName(name);
    Subject subject;
    if(temp.isPresent()) {
      subject =temp.get();
      subject.setStatus("APPROVED");
    }else{
      throw new SubjectNotFoundException();
    }
    subjectRepo.save(subject);
  }

  public List<Subject> getAllSubjectRequests() {
    return subjectRepo.findAllSubjectRequests();
  }

  public List<TeacherToSubjectCountDTO> getSubjectTeacherCounts() {
    List<Object[]> results = subjectRepo.findSubjectTeacherCounts();
    return results.stream()
            .map(result -> new TeacherToSubjectCountDTO((String) result[0], (Long) result[1]))
            .toList();
  }

}
