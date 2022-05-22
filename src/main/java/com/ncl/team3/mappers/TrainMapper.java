package com.ncl.team3.mappers;

import com.ncl.team3.models.Route;
import com.ncl.team3.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainMapper extends JpaRepository<Train,Integer>, PagingAndSortingRepository<Train,Integer> {
}
