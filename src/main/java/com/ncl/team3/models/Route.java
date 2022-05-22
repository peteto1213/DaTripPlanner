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
 * @date 2022/04/08 19:07:10
 */
@ToString(exclude = "routeDetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_route")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Route {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeId;
    private String routeName;


    @ManyToOne
    @JoinColumn(name = "departure_gps")
    private GPS departureGps;

    @ManyToOne
    @JoinColumn(name = "destination_gps")
    private GPS destinationGps;



    @OneToMany(cascade= CascadeType.ALL,mappedBy = "route",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RouteDetail> routeDetail;


}
