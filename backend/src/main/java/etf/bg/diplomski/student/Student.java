package etf.bg.diplomski.student;

import etf.bg.diplomski.user.AppUser;
import etf.bg.diplomski.common.SchoolType;
import etf.bg.diplomski.common.UserStatus;
import etf.bg.diplomski.lesson.Lesson;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value = "Student")
public class Student extends AppUser {
    private SchoolType school;
    private Integer studentGrade;

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private List<Lesson> lesson;

    public Student(String username, String password, String firstName, String lastName, String address, String phone, String email, String imgURL, UserStatus userStatus, SchoolType school, int grade) {
        super(username, password, firstName, lastName, address, phone, email, imgURL, userStatus);
        this.school = school;
        this.studentGrade = grade;
    }

    public Student(String username,String password){
        super(username,password);
    }

    @Override
    public String toString() {
        return this.getFirstName() + this.getLastName();
    }

}
