package wooteco.subway.maps.map.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

import wooteco.subway.maps.line.domain.LineStation;

public class LineStationEdge extends DefaultWeightedEdge {
    private final LineStation lineStation;
    private final Long lineId;

    public LineStationEdge(final LineStation lineStation, final Long lineId) {
        this.lineStation = lineStation;
        this.lineId = lineId;
    }

    public LineStation getLineStation() {
        return lineStation;
    }

    public Long getLineId() {
        return lineId;
    }

    @Override
    protected Object getSource() {
        return this.lineStation.getPreStationId();
    }

    @Override
    protected Object getTarget() {
        return this.lineStation.getStationId();
    }

    public Long extractTargetStationId(final Long preStationId) {
        if (lineStation.getStationId().equals(preStationId)) {
            return lineStation.getPreStationId();
        } else if (lineStation.getPreStationId().equals(preStationId)) {
            return lineStation.getStationId();
        } else {
            throw new RuntimeException();
        }
    }
}
