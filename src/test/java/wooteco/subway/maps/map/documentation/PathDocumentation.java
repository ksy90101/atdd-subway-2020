package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;
import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;
import wooteco.subway.members.member.domain.LoginMember;

@WebMvcTest(controllers = MapController.class)
public class PathDocumentation extends Documentation {
    @MockBean
    private MapService mapService;

    protected TokenResponse tokenResponse;

    @BeforeEach
    public void setUp(final WebApplicationContext context, final RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        tokenResponse = new TokenResponse("token");
    }

    @Test
    void findPath() {
        final StationResponse 교대역 = StationResponse.of(new Station(1L, "교대역"));
        final StationResponse 양재역 = StationResponse.of(new Station(2L, "양재역"));
        final StationResponse 남부터미널역 = StationResponse.of(new Station(3L, "남부터미널역"));
        final LoginMember loginMember = new LoginMember(1L, "brown@woowahan.com", "brown", 25);

        when(mapService.findPath(1L, 3L, PathType.DISTANCE)).thenReturn(new PathResponse(
                Lists.newArrayList(교대역, 남부터미널역, 양재역), 3, 4, 1250));

        given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/paths?source={sourceId}&target={targetId}&type={type}", 1L, 3L, PathType.DISTANCE)
                .then()
                .log().all()
                .apply(document("paths/get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer auth credentials")
                        ),
                        requestParameters(
                                parameterWithName("source").description("출발역 아이디"),
                                parameterWithName("target").description("도착 아이디"),
                                parameterWithName("type").description("조회 값")
                        ),
                        responseFields(
                                fieldWithPath("stations.[]").type(JsonFieldType.ARRAY).description("역 목록"),
                                fieldWithPath("stations.[].id").type(JsonFieldType.NUMBER).description("역 아이디"),
                                fieldWithPath("stations.[].name").type(JsonFieldType.STRING).description("역 이름"),
                                fieldWithPath("duration").type(JsonFieldType.NUMBER).description("소요 시간"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("소요 거리"),
                                fieldWithPath("fare").type(JsonFieldType.NUMBER).description("요금")
                        ))).extract();
    }
}
