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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_plan")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "plan_id")
    private String planId;
    @ManyToOne
    @JoinColumn(name = "email")
    private User user;
    private String name;
    private String createdDate;
    private String createdTime;
    private String status;//deleted/normal    ? locked
}
