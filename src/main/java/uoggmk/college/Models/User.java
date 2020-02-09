package uoggmk.college.Models;

import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean enabled;
    private String role;
    private String firstName;
    private String middleName;
    private String lastName;
    private String studentsBookNumber;
    private String phoneNumber;
}
