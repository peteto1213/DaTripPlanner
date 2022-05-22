package com.ncl.team3.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_castle")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Castle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "castle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer castleId;

    @ManyToOne
    @JoinColumn(name = "castle_gps_id")
    @JsonIgnore
    private GPS castleGps;

    @Column(name = "castle_name")
    private String castleName;

    @Column(name = "castle_contact_phone")
    private String castleContactPhone;

    @Column(name = "castle_description")
    private String castleDescription;


    @Column(name = "castle_cover_pic_address")
    private String castleCoverPicAddress;


    @Column(name = "castle_website_link")
    private String castleWebsiteLink;


    @Column(name = "castle_open_time_info")
    private String castleOpenTimeInfo;

    @Column(name = "castle_Accessibility_status")
    private String castleAccessibilityStatus;

    @Column(name = "castle_Accessibility_info")
    private String castleAccessibilityInfo;

    @Column(name = "castle_estimated_tour_time")
    private Integer castleEstimatedTourTime;



}