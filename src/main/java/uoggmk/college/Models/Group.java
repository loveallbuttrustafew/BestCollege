package uoggmk.college.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "c_groups")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Byte number;
    @Column(nullable = false)
    private Byte course;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<User> students;
}
