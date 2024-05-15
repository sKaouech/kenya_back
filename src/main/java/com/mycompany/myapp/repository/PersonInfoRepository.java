package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Long> {}
