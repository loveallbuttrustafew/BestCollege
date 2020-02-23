package uoggmk.college.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@EqualsAndHashCode(exclude = {"group", "subjects"})
@JsonIgnoreProperties(value = {"group", "subjects"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToMany(mappedBy = "teachers",fetch = FetchType.LAZY)
    private Set<Subject> subjects;

    @Tolerate
    public User(){ }
}
