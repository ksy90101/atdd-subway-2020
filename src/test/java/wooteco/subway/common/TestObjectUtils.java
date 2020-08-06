package wooteco.subway.common;

import java.time.LocalTime;

import org.springframework.test.util.ReflectionTestUtils;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.station.domain.Station;

public class TestObjectUtils {
    public static Station createStation(final Long id, final String name) {
        final Station station = new Station(name);
        ReflectionTestUtils.setField(station, "id", id);
        return station;
    }

    public static Line createLine(final Long id, final String name, final String color) {
        final Line line1 = new Line(name, 0, color, LocalTime.of(05, 30), LocalTime.of(23, 30), 10);
        ReflectionTestUtils.setField(line1, "id", id);
        return line1;
    }
}
