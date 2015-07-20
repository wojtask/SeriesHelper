package pl.kwojtas.serieshelper;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class SeriesEpisodeTest {

    private final String SERIES_TITLE = "Prison Break";
    private final String EPISODE_TITLE = "Cowboys & Indians";
    private final int SEASON_NUMBER = 4;
    private final int EPISODE_NUMBER = 20;
    private final LocalDate EPISODE_PREMIERE = LocalDate.of(2009, Month.MAY, 8);

    @Test
    public void shouldCreateSeriesEpisode() {
        // given

        // when
        SeriesEpisode episode = new SeriesEpisode(SERIES_TITLE, SEASON_NUMBER, EPISODE_NUMBER, EPISODE_TITLE, EPISODE_PREMIERE);

        // then
        assertEquals(SERIES_TITLE, episode.getSeriesTitle());
        assertEquals(EPISODE_TITLE, episode.getEpisodeTitle());
        assertEquals(SEASON_NUMBER, episode.getSeasonNumber());
        assertEquals(EPISODE_NUMBER, episode.getEpisodeNumber());
        assertEquals(EPISODE_PREMIERE, episode.getEpisodePremiere());
    }

    @Test
    public void shouldReturnEpisodeSignature() {
        // given
        SeriesEpisode episode = new SeriesEpisode(SERIES_TITLE, SEASON_NUMBER, EPISODE_NUMBER, EPISODE_TITLE, EPISODE_PREMIERE);

        // when
        String actualSignature = episode.getSignature();

        // then
        String expectedSignature = "Prison Break 04x20";
        assertEquals(expectedSignature, actualSignature);
    }

}
