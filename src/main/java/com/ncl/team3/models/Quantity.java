package com.ncl.team3.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/21 14:40:14
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_quantity")
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quantityId;
    private Integer quantity;
}
