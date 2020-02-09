package uoggmk.college.Models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean enabled;
    private String firstName;
    private String middleName;
    private String lastName;
    private String studentsBookNumber;
    private String phoneNumber;
}
