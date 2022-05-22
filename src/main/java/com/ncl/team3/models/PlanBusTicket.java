package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_plan_bus_ticket")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanBusTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "plan_bus_ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planBusTicketId;


    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_ticket_id")
    private BusTicket busTicket;


    private Integer busTicketQuantity;

}
