package uoggmk.college.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(exclude = {"group"})
@JsonIgnoreProperties(value = {"group"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String firstName;
    private String middleName;
    private String lastName;
    private String studentsBookNumber;
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;
}
