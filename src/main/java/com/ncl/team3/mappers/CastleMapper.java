package com.ncl.team3.mappers;


import com.ncl.team3.models.Castle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastleMapper extends JpaRepository<Castle,Integer> ,PagingAndSortingRepository<Castle,Integer> {
    Castle findByCastleId(Integer id);
    Castle findCastleByCastleName(String castleName);

}
