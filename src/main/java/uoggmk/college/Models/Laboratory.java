package uoggmk.college.Models;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "laboratories")
@Data
@Builder
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filepath;
    private String originalName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "subjectId")
    private Subject subject;

    @Tolerate
    public Laboratory(){ }
}
