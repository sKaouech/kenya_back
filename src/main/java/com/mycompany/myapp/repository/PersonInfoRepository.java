package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Long> {
    Optional<PersonInfo> findByNationalID(String id);
}
