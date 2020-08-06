package wooteco.subway.maps.map.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.graph.WeightedMultigraph;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;

public class SubwayGraph extends WeightedMultigraph<Long, LineStationEdge> {
    public SubwayGraph(final Class edgeClass) {
        super(edgeClass);
    }

    public void addVertexWith(final List<Line> lines) {
        // 지하철 역(정점)을 등록
        lines.stream()
                .flatMap(it -> it.getStationInOrder().stream())
                .map(LineStation::getStationId)
                .distinct()
                .collect(Collectors.toList())
                .forEach(this::addVertex);
    }

    public void addEdge(final List<Line> lines, final PathType type) {
        // 지하철 역의 연결 정보(간선)을 등록
        for (final Line line : lines) {
            line.getStationInOrder().stream()
                    .filter(it -> it.getPreStationId() != null)
                    .forEach(it -> addEdge(type, it, line));
        }
    }

    private void addEdge(final PathType type, final LineStation lineStation, final Line line) {
        final LineStationEdge lineStationEdge = new LineStationEdge(lineStation, line.getId());
        addEdge(lineStation.getPreStationId(), lineStation.getStationId(), lineStationEdge);
        setEdgeWeight(lineStationEdge, type.findWeightOf(lineStation));
    }
}
