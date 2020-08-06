package wooteco.subway.maps.map.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.MapResponse;
import wooteco.subway.maps.map.dto.PathResponse;

@RestController
public class MapController {
    private final MapService mapService;

    public MapController(final MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> findPath(
            @RequestParam final Long source,
            @RequestParam final Long target,
            @RequestParam final PathType type) {
        return ResponseEntity.ok(mapService.findPath(source, target, type));
    }

    @GetMapping("/maps")
    public ResponseEntity<MapResponse> findMap() {
        final MapResponse response = mapService.findMap();
        return ResponseEntity.ok(response);
    }
}
