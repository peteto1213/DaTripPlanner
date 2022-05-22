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
@Table(name = "t_plan_castle_ticket")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanCastleTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "plan_castle_ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("plan_castle_ticket_id")
    private Integer planCastleTicketId;

    @JoinColumn(name = "plan_id")
    @ManyToOne
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "castle_ticket_id")
    private CastleTicket castleTicket;


    private Integer castleTicketQuantity;


}
