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
 * @date 2022/04/08 19:18:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_bus_operator")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusOperator implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @JsonProperty("bus_operator_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer busCompanyId;


    private String name;
    private String status;
    private String contactPhone;
    private String websiteLink;

}
