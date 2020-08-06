package wooteco.subway.maps.line.dto;

import java.time.LocalTime;

import wooteco.subway.maps.line.domain.Line;

public class LineRequest {
    private String name;
    private int extraFare;
    private String color;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer intervalTime;

    public LineRequest() {
    }

    public LineRequest(
            final String name, final int extraFare, final String color, final LocalTime startTime,
            final LocalTime endTime,
            final Integer intervalTime) {
        this.name = name;
        this.extraFare = extraFare;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public Line toLine() {
        return new Line(name, extraFare, color, startTime, endTime, intervalTime);
    }
}
