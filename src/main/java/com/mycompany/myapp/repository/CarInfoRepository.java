package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CarInfo;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CarInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
    @Query(value = "select r from CarInfo r where r.personInfo.nationalID=?1")
    List<CarInfo> findAllByPerson(String id);
}
