package io.github.zyrouge.symphony.services.subsonic;

import java.util.HashMap;
import java.util.Map;

import io.github.zyrouge.symphony.services.subsonic.api.albumsonglist.AlbumSongListClient;
import io.github.zyrouge.symphony.services.subsonic.api.bookmarks.BookmarksClient;
import io.github.zyrouge.symphony.services.subsonic.api.browsing.BrowsingClient;
import io.github.zyrouge.symphony.services.subsonic.api.internetradio.InternetRadioClient;
import io.github.zyrouge.symphony.services.subsonic.api.mediaannotation.MediaAnnotationClient;
import io.github.zyrouge.symphony.services.subsonic.api.medialibraryscanning.MediaLibraryScanningClient;
import io.github.zyrouge.symphony.services.subsonic.api.mediaretrieval.MediaRetrievalClient;
import io.github.zyrouge.symphony.services.subsonic.api.open.OpenClient;
import io.github.zyrouge.symphony.services.subsonic.api.playlist.PlaylistClient;
import io.github.zyrouge.symphony.services.subsonic.api.podcast.PodcastClient;
import io.github.zyrouge.symphony.services.subsonic.api.searching.SearchingClient;
import io.github.zyrouge.symphony.services.subsonic.api.sharing.SharingClient;
import io.github.zyrouge.symphony.services.subsonic.api.system.SystemClient;
import io.github.zyrouge.symphony.services.subsonic.base.Version;

public class Subsonic {
    private static final Version API_MAX_VERSION = Version.of("1.15.0");

    private final Version apiVersion = API_MAX_VERSION;
    private final SubsonicPreferences preferences;

    private SystemClient systemClient;
    private BrowsingClient browsingClient;
    private MediaRetrievalClient mediaRetrievalClient;
    private PlaylistClient playlistClient;
    private SearchingClient searchingClient;
    private AlbumSongListClient albumSongListClient;
    private MediaAnnotationClient mediaAnnotationClient;
    private PodcastClient podcastClient;
    private MediaLibraryScanningClient mediaLibraryScanningClient;
    private BookmarksClient bookmarksClient;
    private InternetRadioClient internetRadioClient;
    private SharingClient sharingClient;
    private OpenClient openClient;

    public Subsonic(SubsonicPreferences preferences) {
        this.preferences = preferences;
    }

    public Version getApiVersion() {
        return apiVersion;
    }

    public SystemClient getSystemClient() {
        if (systemClient == null) {
            systemClient = new SystemClient(this);
        }
        return systemClient;
    }

    public BrowsingClient getBrowsingClient() {
        if (browsingClient == null) {
            browsingClient = new BrowsingClient(this);
        }
        return browsingClient;
    }

    public MediaRetrievalClient getMediaRetrievalClient() {
        if (mediaRetrievalClient == null) {
            mediaRetrievalClient = new MediaRetrievalClient(this);
        }
        return mediaRetrievalClient;
    }

    public PlaylistClient getPlaylistClient() {
        if (playlistClient == null) {
            playlistClient = new PlaylistClient(this);
        }
        return playlistClient;
    }

    public SearchingClient getSearchingClient() {
        if (searchingClient == null) {
            searchingClient = new SearchingClient(this);
        }
        return searchingClient;
    }

    public AlbumSongListClient getAlbumSongListClient() {
        if (albumSongListClient == null) {
            albumSongListClient = new AlbumSongListClient(this);
        }
        return albumSongListClient;
    }

    public MediaAnnotationClient getMediaAnnotationClient() {
        if (mediaAnnotationClient == null) {
            mediaAnnotationClient = new MediaAnnotationClient(this);
        }
        return mediaAnnotationClient;
    }

    public PodcastClient getPodcastClient() {
        if (podcastClient == null) {
            podcastClient = new PodcastClient(this);
        }
        return podcastClient;
    }

    public MediaLibraryScanningClient getMediaLibraryScanningClient() {
        if (mediaLibraryScanningClient == null) {
            mediaLibraryScanningClient = new MediaLibraryScanningClient(this);
        }
        return mediaLibraryScanningClient;
    }

    public BookmarksClient getBookmarksClient() {
        if (bookmarksClient == null) {
            bookmarksClient = new BookmarksClient(this);
        }
        return bookmarksClient;
    }

    public InternetRadioClient getInternetRadioClient() {
        if (internetRadioClient == null) {
            internetRadioClient = new InternetRadioClient(this);
        }
        return internetRadioClient;
    }

    public SharingClient getSharingClient() {
        if (sharingClient == null) {
            sharingClient = new SharingClient(this);
        }
        return sharingClient;
    }

    public OpenClient getOpenClient() {
        if (openClient == null) {
            openClient = new OpenClient(this);
        }
        return openClient;
    }

    public String getUrl() {
        String url = preferences.getServerUrl() + "/rest/";
        return url.replace("//rest", "/rest");
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("u", preferences.getUsername());

        if (preferences.getAuthentication().getPassword() != null)
            params.put("p", preferences.getAuthentication().getPassword());
        if (preferences.getAuthentication().getSalt() != null)
            params.put("s", preferences.getAuthentication().getSalt());
        if (preferences.getAuthentication().getToken() != null)
            params.put("t", preferences.getAuthentication().getToken());

        params.put("v", getApiVersion().getVersionString());
        params.put("c", preferences.getClientName());
        params.put("f", "json");

        return params;
    }
}
