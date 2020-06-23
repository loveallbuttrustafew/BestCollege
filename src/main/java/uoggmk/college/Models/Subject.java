package uoggmk.college.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subjects")
@Data
@EqualsAndHashCode(exclude = {"teachers", "laboratories"})
@JsonIgnoreProperties(value = {"group", "laboratories"})
@ToString
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "groupId")
    private Group group;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacherId"),
            inverseJoinColumns = @JoinColumn(name = "subjectId")
    )
    private Set<User> teachers;
    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Laboratory> laboratories;
}
