package com.ncl.team3.mappers;

import com.ncl.team3.models.GPS;
import com.ncl.team3.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 19:14:41
 */
@Repository
public interface GPSMapper extends JpaRepository<GPS,Integer>, PagingAndSortingRepository<GPS,Integer> {
}
