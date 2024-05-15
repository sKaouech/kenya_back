package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CarInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CarInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {}
