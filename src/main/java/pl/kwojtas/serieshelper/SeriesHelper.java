package pl.kwojtas.serieshelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class SeriesHelper {

    public static void main(String[] args) {
        SeriesEpisode episode = new SeriesEpisode("Chuck", 6, 1, "Chuck versus Future", LocalDate.of(2015, Month.JULY, 23));
        try {
            EventCreator.createEventForEpisode(episode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
