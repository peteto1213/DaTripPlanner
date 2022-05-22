package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_castle_ticket")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CastleTicket implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "castle_ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer castleTicketId;

    @ManyToOne
    @JoinColumn(name ="castle_id")
    private Castle castle;

    private BigDecimal price;

    private String date;

    private String time;

    private String type;

    private String status;

    private int quantity;



}
