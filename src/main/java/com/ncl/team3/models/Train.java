package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/10 23:33:46
 */
@Getter
@Setter
@ToString(exclude = "trainTimetable")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_train")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Integer trainId;

    @ManyToOne
    @JoinColumn(name = "train_operator")
    private TrainOperator trainOperator;

    @Column(name = "train_type")
    private String trainType;

    @Column(name = "train_name")
    private String trainName;


    @OneToMany(mappedBy = "train",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TrainTimetable> trainTimetable;



}
