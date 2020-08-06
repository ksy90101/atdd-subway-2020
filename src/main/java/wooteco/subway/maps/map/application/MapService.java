package wooteco.subway.maps.map.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.maps.line.application.LineService;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.line.dto.LineStationResponse;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.map.dto.MapResponse;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.dto.PathResponseAssembler;
import wooteco.subway.maps.station.application.StationService;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;

@Service
@Transactional
public class MapService {
    private final LineService lineService;
    private final StationService stationService;
    private final PathService pathService;

    public MapService(final LineService lineService, final StationService stationService,
            final PathService pathService) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.pathService = pathService;
    }

    public MapResponse findMap() {
        final List<Line> lines = lineService.findLines();
        final Map<Long, Station> stations = findStations(lines);

        final List<LineResponse> lineResponses = lines.stream()
                .map(it -> LineResponse.of(it, extractLineStationResponses(it, stations)))
                .collect(Collectors.toList());

        return new MapResponse(lineResponses);
    }

    public PathResponse findPath(final Long source, final Long target, final PathType type) {
        final List<Line> lines = lineService.findLines();
        final SubwayPath subwayPath = pathService.findPath(lines, source, target, type);
        final Map<Long, Station> stations = stationService.findStationsByIds(subwayPath.extractStationId());
        final int maxLineExtraFare = subwayPath.getLineStationEdges()
                .stream()
                .map(LineStationEdge::getLineId)
                .map(lineService::findLineById)
                .map(Line::getExtraFare)
                .max(Integer::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("값이 없습니다."));

        return PathResponseAssembler.assemble(subwayPath, stations, maxLineExtraFare);
    }

    private Map<Long, Station> findStations(final List<Line> lines) {
        final List<Long> stationIds = lines.stream()
                .flatMap(it -> it.getStationInOrder().stream())
                .map(LineStation::getStationId)
                .collect(Collectors.toList());

        return stationService.findStationsByIds(stationIds);
    }

    private List<LineStationResponse> extractLineStationResponses(final Line line, final Map<Long, Station> stations) {
        return line.getStationInOrder().stream()
                .map(it -> LineStationResponse.of(line.getId(), it,
                        StationResponse.of(stations.get(it.getStationId()))))
                .collect(Collectors.toList());
    }
}
