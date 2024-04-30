package io.github.zyrouge.symphony.services.subsonic.api.medialibraryscanning;

import android.util.Log;

import io.github.zyrouge.symphony.services.subsonic.RetrofitClient;
import com.cappielloantonio.tempo.subsonic.base.ApiResponse;

import io.github.zyrouge.symphony.services.subsonic.Subsonic;
import retrofit2.Call;

public class MediaLibraryScanningClient {
    private static final String TAG = "MediaLibraryScanningClient";

    private final Subsonic subsonic;
    private final MediaLibraryScanningService mediaLibraryScanningService;

    public MediaLibraryScanningClient(Subsonic subsonic) {
        this.subsonic = subsonic;
        this.mediaLibraryScanningService = new RetrofitClient(subsonic).getRetrofit().create(MediaLibraryScanningService.class);
    }

    public Call<ApiResponse> startScan() {
        Log.d(TAG, "startScan()");
        return mediaLibraryScanningService.startScan(subsonic.getParams());
    }

    public Call<ApiResponse> getScanStatus() {
        Log.d(TAG, "getScanStatus()");
        return mediaLibraryScanningService.getScanStatus(subsonic.getParams());
    }
}
