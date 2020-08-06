package wooteco.subway.maps.map.ui;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wooteco.security.core.AuthenticationPrincipal;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.MapResponse;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.members.member.domain.LoginMember;

@RestController
public class MapController {
    private final MapService mapService;

    public MapController(final MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping(value = "/paths", headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<PathResponse> findPath(
            @AuthenticationPrincipal final LoginMember loginMember,
            @RequestParam final Long source,
            @RequestParam final Long target,
            @RequestParam final PathType type) {
        return ResponseEntity.ok(mapService.findPath(source, target, type, loginMember));
    }

    @GetMapping(value = "/paths")
    public ResponseEntity<PathResponse> findPath(
            @RequestParam final Long source,
            @RequestParam final Long target,
            @RequestParam final PathType type) {
        return ResponseEntity.ok(mapService.findPath(source, target, type, null));
    }

    @GetMapping("/maps")
    public ResponseEntity<MapResponse> findMap() {
        final MapResponse response = mapService.findMap();
        return ResponseEntity.ok(response);
    }
}
