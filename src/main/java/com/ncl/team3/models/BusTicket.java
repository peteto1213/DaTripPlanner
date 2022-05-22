package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 19:25:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_bus_ticket")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_ticket_id")
    private Integer busTicketId;


    @ManyToOne
    @JoinColumn(name = "bus_timetable_id")
    private BusTimetable busTimetable;

    @ManyToOne
    @JoinColumn(name = "quantity_id")
    private Quantity quantity;

    private String type;
    private BigDecimal price;
    private String status;




}
