package etf.bg.diplomski.student;

import etf.bg.diplomski.common.SchoolType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static etf.bg.diplomski.common.ApplicationURL.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
@ExtendWith(SpringExtension.class)
public class StudentControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void SuccessfulRegistrationOfStudent() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post(STUDENT_URL + ADD_STUDENT).queryParam("username", "petar").
                queryParam("password", "Petrovic@123").queryParam("firstName", "Petar").
                queryParam("lastName", "Petrovic").queryParam("address","").queryParam("phone","").
                queryParam("email","petar@yahoo.com").queryParam("images","null").
                queryParam("school", String.valueOf(SchoolType.ELEMENTARY)).queryParam("grade","6");
        mvc.perform(request).andExpect(status().isOk());

    }

    @Test
    public void UnsuccessfulRegistrationOfStudent() throws Exception {
        RequestBuilder request=MockMvcRequestBuilders.post(STUDENT_URL+ADD_STUDENT).queryParam("username", "petar").
                queryParam("password", "Petrovic@123").queryParam("firstName", "Petar").
                queryParam("lastName", "Petrovic").queryParam("address","").queryParam("phone","").
                queryParam("email","petar@yahoo.com").queryParam("images","null").
                queryParam("school", String.valueOf(SchoolType.ELEMENTARY)).queryParam("grade","6");
        mvc.perform(request).andExpect(status().isBadRequest());
    }


    @Test
    public void GettingAllStudents() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(STUDENT_URL + GET_STUDENTS);
        MvcResult result=mvc.perform(request).andReturn();

        Assertions.assertEquals(200,result.getResponse().getStatus());
    }
}

