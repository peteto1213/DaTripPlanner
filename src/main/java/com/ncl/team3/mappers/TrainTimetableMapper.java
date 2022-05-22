package com.ncl.team3.mappers;

import com.ncl.team3.models.TrainTicket;
import com.ncl.team3.models.TrainTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/10 23:41:32
 */
@Repository
public interface TrainTimetableMapper
        extends
        JpaSpecificationExecutor<TrainTimetable>,
        JpaRepository<TrainTimetable,Integer>,
        PagingAndSortingRepository<TrainTimetable,Integer>
{
}
