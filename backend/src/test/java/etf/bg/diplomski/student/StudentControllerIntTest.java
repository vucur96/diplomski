package etf.bg.diplomski.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(MockitoExtension.class)
public class StudentControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void SuccessfulRegistrationOfStudent()  {



    }

    @Test
    public void UnsuccessfulRegistrationOfStudent() {
    }


    @Test
    public void GettingAllStudents(){
    }
}

