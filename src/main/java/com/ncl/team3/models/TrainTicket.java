package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/10 12:31:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_train_ticket")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainTicket {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainTicketId;

    @JoinColumn(name = "ticket_quantity_id")
    @ManyToOne
    private Quantity quantity;

    @ManyToOne
    @JoinColumn(name = "train_timetable_id")
    private TrainTimetable trainTimetable;
    private String type;
    private BigDecimal price;

    private String status;









}
