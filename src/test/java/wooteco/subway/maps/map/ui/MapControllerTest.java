package wooteco.subway.maps.map.ui;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.MapResponse;
import wooteco.subway.maps.map.dto.PathResponse;

public class MapControllerTest {
    @Test
    void findPath() {
        final MapService mapService = mock(MapService.class);
        final MapController controller = new MapController(mapService);
        when(mapService.findPath(anyLong(), anyLong(), any())).thenReturn(new PathResponse());

        final ResponseEntity<PathResponse> entity = controller.findPath(1L, 2L, PathType.DISTANCE);

        assertThat(entity.getBody()).isNotNull();
    }

    @Test
    void findMap() {
        final MapService mapService = mock(MapService.class);
        final MapController controller = new MapController(mapService);

        final ResponseEntity<MapResponse> entity = controller.findMap();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(mapService).findMap();
    }
}
