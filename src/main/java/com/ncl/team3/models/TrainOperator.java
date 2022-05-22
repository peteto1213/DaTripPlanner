package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/10 23:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_train_operator")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainOperatorId;
    private String name;
    private String status;
    @JsonProperty("contact_phone")
    private String contactPhone;
    @JsonProperty("website_link")
    private String websiteLink;
}
