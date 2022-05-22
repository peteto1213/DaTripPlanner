package com.ncl.team3.mappers;

import com.ncl.team3.models.Bus;
import com.ncl.team3.models.Castle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 19:17:55
 */
@Repository
public interface BusMapper extends JpaRepository<Bus,Integer>, PagingAndSortingRepository<Bus,Integer> {
    Bus findByBusId(Integer id);
    @Query("from Bus ")
    Page<Bus> getAllBus(Pageable pageable);
    
}
