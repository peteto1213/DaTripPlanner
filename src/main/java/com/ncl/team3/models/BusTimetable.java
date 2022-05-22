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
 * @date 2022/04/10 22:48:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_bus_timetable")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusTimetable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_timetable_id")
    private Integer busTimetableId;

    @ManyToOne
    @JoinColumn(name = "route_detail_id")
    private RouteDetail routeDetail;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;


    private String date;
    private String time;
    private Integer travelTime;
    private String status;


    @JsonIgnore
    @OneToMany(mappedBy = "busTimetable",fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<BusTicket> busTicket;
}
