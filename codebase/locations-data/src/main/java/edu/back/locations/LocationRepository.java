package edu.back.locations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationNumber(String locationNumber);

}
