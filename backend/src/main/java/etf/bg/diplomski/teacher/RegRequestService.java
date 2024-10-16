package etf.bg.diplomski.teacher;

import etf.bg.diplomski.user.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static etf.bg.diplomski.common.UserStatus.ACTIVE;
import static etf.bg.diplomski.teacher.TeacherRequestStatus.APPROVED;
import static etf.bg.diplomski.teacher.TeacherRequestStatus.DECLINED;

@Service
public class RegRequestService {

  @Autowired private RegisterRreqRepo registerReqRepo;

  @Autowired private AppUserRepo appUserRepo;

  @Autowired private TeacherDTOMapper teacherDTOMapper;

  public List<RegistrationRequest> getAllRequests() {
    return registerReqRepo.findAll().stream().toList();
  }

  public List<RegisterRequestDTO> getAllPendingRequests() {
    List<RegistrationRequest> registerRequests=registerReqRepo.findAllPendingRequests();

    List<RegisterRequestDTO>  retList=new ArrayList<>();
    for(RegistrationRequest rr:registerRequests){
      RegisterRequestDTO temp=new RegisterRequestDTO(rr.getId(),teacherDTOMapper.apply(rr.getTeacher()),rr.getStatus(),rr.getCreated(),rr.getChanged());
      retList.add(temp);
    }
    return retList;
  }

  public String acceptRequest(Long id) {
    RegistrationRequest req = registerReqRepo.findById(id).get();
    req.setStatus(APPROVED);
    req.setChanged(LocalDateTime.now());
    req.getTeacher().setStatus(ACTIVE);
    appUserRepo.save(req.getTeacher());
    registerReqRepo.save(req);

    return "Request accepted!";
  }

  public String rejectRequest(Long id) {
    RegistrationRequest req = registerReqRepo.findById(id).get();
    req.setStatus(DECLINED);
    req.setChanged(LocalDateTime.now());
    registerReqRepo.save(req);
    return "Request rejected!";
  }
}
