package pl.kwojtas.serieshelper;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class EventCreator {

    public static final Logger LOGGER = LoggerFactory.getLogger(EventCreator.class);

    private static final String APPLICATION_NAME = "Series Helper";

    private static final File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-serieshelper");

    private static final String SECRET_FILE = "/client_secret.json";

    private static FileDataStoreFactory DATA_STORE_FACTORY;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static HttpTransport HTTP_TRANSPORT;

    private static final List<String> SCOPES = ImmutableList.of(CalendarScopes.CALENDAR);

    private static final String CALENDAR_ID = "mdt925v19giu1po44lh5vhj44k@group.calendar.google.com";

    private EventCreator() {
    }

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static Credential authorize() throws IOException {
        InputStream in = EventCreator.class.getClass().getResourceAsStream(SECRET_FILE);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    private static Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void createEventForEpisode(SeriesEpisode episode) throws IOException {
        Event event = new Event().setSummary(episode.getSignature()).setDescription(episode.getEpisodeTitle());

        DateTime startDateTime = new DateTime(episode.getEpisodePremiere().toString());
        EventDateTime start = new EventDateTime().setDate(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(episode.getEpisodePremiere().plusDays(1).toString());
        EventDateTime end = new EventDateTime().setDate(endDateTime);
        event.setEnd(end);

        event = getCalendarService().events().insert(CALENDAR_ID, event).execute();
        LOGGER.info("Event created: {}", event.getHtmlLink());
    }
}
