package wooteco.subway.maps.config;

import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineRepository;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.domain.StationRepository;

@Component
@Profile("!documentation")
public class DataLoaderConfig implements CommandLineRunner {
    private final StationRepository stationRepository;
    private final LineRepository lineRepository;

    public DataLoaderConfig(final StationRepository stationRepository, final LineRepository lineRepository) {
        this.stationRepository = stationRepository;
        this.lineRepository = lineRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        final Station station1 = new Station("강남역");
        final Station station2 = new Station("교대역");
        final Station station3 = new Station("양재역");
        final Station station4 = new Station("남부터미널역");
        stationRepository.saveAll(Lists.newArrayList(station1, station2, station3, station4));

        final Line line1 = new Line("신분당선", 100, "red lighten-1", LocalTime.now(), LocalTime.now(), 10);
        final Line line2 = new Line("2호선", 0, "green lighten-1", LocalTime.now(), LocalTime.now(), 10);
        final Line line3 = new Line("3호선", 0, "orange darken-1", LocalTime.now(), LocalTime.now(), 10);

        line1.addLineStation(new LineStation(1L, null, 0, 0));
        line1.addLineStation(new LineStation(3L, 1L, 3, 1));

        line2.addLineStation(new LineStation(2L, null, 0, 0));
        line2.addLineStation(new LineStation(1L, 2L, 3, 1));

        line3.addLineStation(new LineStation(2L, null, 0, 0));
        line3.addLineStation(new LineStation(4L, 2L, 2, 10));
        line3.addLineStation(new LineStation(3L, 4L, 2, 10));

        lineRepository.saveAll(Lists.newArrayList(line1, line2, line3));
    }
}
