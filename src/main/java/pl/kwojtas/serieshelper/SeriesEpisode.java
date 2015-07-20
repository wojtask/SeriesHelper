package pl.kwojtas.serieshelper;

import java.time.LocalDate;

public class SeriesEpisode {

    private String seriesTitle;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeTitle;
    private LocalDate episodePremiere;

    public SeriesEpisode(String seriesTitle,
                         int seasonNumber,
                         int episodeNumber,
                         String episodeTitle,
                         LocalDate episodePremiere) {
        this.seriesTitle = seriesTitle;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.episodePremiere = episodePremiere;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public LocalDate getEpisodePremiere() {
        return episodePremiere;
    }

    public String getSignature() {
        return String.format("%s %02dx%02d", seriesTitle, seasonNumber, episodeNumber);
    }
}
