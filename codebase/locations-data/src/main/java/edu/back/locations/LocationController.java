package edu.back.locations;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
@Log4j2
public class LocationController {

    private LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public List<Location> justList(HttpEntity<String> entity) {
        logTraceId(entity);
        return locationRepository.findAll();
    }

    private void logTraceId(HttpEntity<String> entity) {
        log.info("TraceId {}", entity.getHeaders().get("TraceId"));
    }

    @GetMapping("/{locationNumber}")
    public Location byNumber(HttpEntity<String> entity,
            @PathVariable("locationNumber") String locationNumber) throws InterruptedException {
        log.info("Request with TraceId {}", entity.getHeaders().get("TraceId"));
        return locationRepository.findByLocationNumber(locationNumber);
    }
}
