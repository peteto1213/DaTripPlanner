package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.*;
import com.ncl.team3.mappers.BusTimetableMapper;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 19:21:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_route_detail")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeDetailId;


    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;


    @JoinColumn(name = "start_point_gps_id")
    @ManyToOne
    private GPS startPointGps;


    @JoinColumn(name = "end_point_gps_id")
    @ManyToOne
    private GPS endPointGps;

    private String type;

    private String name;

    private Integer order;



}
