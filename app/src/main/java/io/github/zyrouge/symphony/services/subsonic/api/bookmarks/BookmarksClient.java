package io.github.zyrouge.symphony.services.subsonic.api.bookmarks;

import android.util.Log;

import io.github.zyrouge.symphony.services.subsonic.RetrofitClient;
import com.cappielloantonio.tempo.subsonic.base.ApiResponse;

import java.util.List;

import io.github.zyrouge.symphony.services.subsonic.Subsonic;
import retrofit2.Call;

public class BookmarksClient {
    private static final String TAG = "BookmarksClient";

    private final Subsonic subsonic;
    private final BookmarksService bookmarksService;

    public BookmarksClient(Subsonic subsonic) {
        this.subsonic = subsonic;
        this.bookmarksService = new RetrofitClient(subsonic).getRetrofit().create(BookmarksService.class);
    }

    public Call<ApiResponse> getPlayQueue() {
        Log.d(TAG, "getPlayQueue()");
        return bookmarksService.getPlayQueue(subsonic.getParams());
    }

    public Call<ApiResponse> savePlayQueue(List<String> ids, String current, long position) {
        Log.d(TAG, "savePlayQueue()");
        return bookmarksService.savePlayQueue(subsonic.getParams(), ids, current, position);
    }
}
