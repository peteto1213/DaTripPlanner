package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 19:16:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_bus")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer busId;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private BusOperator busOperator;


    @Column(name = "bus_type")
    private String busType;


    @Column(name = "bus_name")
    private String busName;

    @JsonIgnore
    @OneToMany(mappedBy = "bus",cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<BusTimetable> busTimetable;


}
