package com.ncl.team3.mappers;

import com.ncl.team3.models.CastleTicket;
import com.ncl.team3.models.Plan;
import com.ncl.team3.models.PlanCastleTicket;
import com.ncl.team3.models.User;
import com.ncl.team3.response.ViewPlanResponse;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanMapper extends JpaRepository<Plan,Integer>, PagingAndSortingRepository<Plan,Integer> {
    @Query(value = "from Plan where planId = ?1")
    Plan findByPlanId(String id);

    @Query("from Plan p where p.user.email = ?1 and p.status <> 'deleted'")
    Page<Plan> allPlans(String email, Pageable pageable);

    @Query("select p.name from Plan p,PlanCastleTicket pc,OrderCastleTicket oc where oc.castleTicket = ?1 and pc.castleTicket = ?1 and pc.plan.planId = p.planId")
    List<String> findPlanNameByOrderCastleTicketId(CastleTicket castleTicket);
}