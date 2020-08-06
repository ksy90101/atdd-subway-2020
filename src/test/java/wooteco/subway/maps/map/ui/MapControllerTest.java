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
import wooteco.subway.members.member.application.MemberService;
import wooteco.subway.members.member.domain.LoginMember;

public class MapControllerTest {
    @Test
    void findPath() {
        final MapService mapService = mock(MapService.class);
        final MapController controller = new MapController(mapService);
        final MemberService memberService = mock(MemberService.class);

        final LoginMember loginMember = new LoginMember(1L, "brown@gmail.com", "brown", 25);

        when(mapService.findPath(anyLong(), anyLong(), any(), any())).thenReturn(new PathResponse());

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
