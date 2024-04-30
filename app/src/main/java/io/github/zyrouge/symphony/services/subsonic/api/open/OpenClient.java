package io.github.zyrouge.symphony.services.subsonic.api.open;

import android.util.Log;

import io.github.zyrouge.symphony.services.subsonic.RetrofitClient;
import com.cappielloantonio.tempo.subsonic.base.ApiResponse;

import io.github.zyrouge.symphony.services.subsonic.Subsonic;
import retrofit2.Call;

public class OpenClient {
    private static final String TAG = "OpenClient";

    private final Subsonic subsonic;
    private final OpenService openService;

    public OpenClient(Subsonic subsonic) {
        this.subsonic = subsonic;
        this.openService = new RetrofitClient(subsonic).getRetrofit().create(OpenService.class);
    }

    public Call<ApiResponse> getLyricsBySongId(String id) {
        Log.d(TAG, "getLyricsBySongId()");
        return openService.getLyricsBySongId(subsonic.getParams(), id);
    }
}
