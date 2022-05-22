package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/10 23:32:02
 */
@Getter
@Setter
@ToString(exclude = "routeDetail")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_train_timetable")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainTimetableId;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @JoinColumn(name = "route_detail_id")
    @ManyToOne
    private RouteDetail routeDetail;
    private String date;
    private String time;
    private String status;
    private Integer travelTime;
}
