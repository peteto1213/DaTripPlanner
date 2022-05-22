package com.ncl.team3.mappers;

import com.ncl.team3.models.PlanTrainTicket;
import com.ncl.team3.models.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/26 10:00:53
 */
@Repository
public interface  QuantityMapper extends JpaRepository<Quantity,Integer> {

}
