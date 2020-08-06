package wooteco.subway.maps.map.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class SubwayPath {
    private static final int BASIC_FARE = 1250;

    private final List<LineStationEdge> lineStationEdges;

    public SubwayPath(final List<LineStationEdge> lineStationEdges) {
        this.lineStationEdges = lineStationEdges;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractStationId() {
        final List<Long> stationIds = Lists.newArrayList(lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
                .map(it -> it.getLineStation().getStationId())
                .collect(Collectors.toList()));

        return stationIds;
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public int calculateFare(final int distance, final int maxLineExtraFare) {
        final int fare = BASIC_FARE + maxLineExtraFare + extraFareByDistance(distance);
        return fare;
        // return fare - loginMember.discountFare(fare);
    }

    private int extraFareByDistance(final int distance) {
        int extraFare = 0;
        if (distance > 10 && distance <= 50) {
            extraFare = (int)((Math.ceil((distance - 10) / 5) + 1) * 100);
        }
        if (distance > 50) {
            extraFare = (int)((Math.ceil((distance - 51) / 8) + 1) * 100);
        }
        return extraFare;
    }
}
