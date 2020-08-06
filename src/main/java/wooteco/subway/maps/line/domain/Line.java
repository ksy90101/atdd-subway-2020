package wooteco.subway.maps.line.domain;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import wooteco.subway.common.domain.BaseEntity;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private int extraFare;
    private String color;
    private LocalTime startTime;
    private LocalTime endTime;
    private int intervalTime;
    @Embedded
    private final LineStations lineStations = new LineStations();

    public Line() {
    }

    public Line(final String name, final int extraFare, final String color, final LocalTime startTime,
            final LocalTime endTime,
            final int intervalTime) {
        this.name = name;
        this.extraFare = extraFare;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public void update(final Line line) {
        this.name = line.getName();
        this.extraFare = line.getExtraFare();
        this.startTime = line.getStartTime();
        this.endTime = line.getEndTime();
        this.intervalTime = line.getIntervalTime();
        this.color = line.getColor();
    }

    public void addLineStation(final LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public void removeLineStationById(final Long stationId) {
        lineStations.removeByStationId(stationId);
    }

    public List<LineStation> getStationInOrder() {
        return lineStations.getStationsInOrder();
    }

    public Long getId() {
        return id;
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

    public int getIntervalTime() {
        return intervalTime;
    }

    public LineStations getLineStations() {
        return lineStations;
    }

    public int getExtraFare() {
        return extraFare;
    }
}
