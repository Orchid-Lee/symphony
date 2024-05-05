package io.github.zyrouge.symphony.services.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.cappielloantonio.tempo.subsonic.base.ApiResponse;

import java.util.List;

import io.github.zyrouge.symphony.App;
import io.github.zyrouge.symphony.services.interfaces.SystemCallback;
import io.github.zyrouge.symphony.services.subsonic.models.OpenSubsonicExtension;
import io.github.zyrouge.symphony.services.subsonic.models.ResponseStatus;
import io.github.zyrouge.symphony.services.subsonic.models.SubsonicResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SystemRepository {
    public void checkUserCredential(SystemCallback callback) {
        App.getSubsonicClientInstance(false)
                .getSystemClient()
                .ping()
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getSubsonicResponse().getStatus().equals(ResponseStatus.FAILED)) {
                                callback.onError(new Exception(response.body().getSubsonicResponse().getError().getCode() + " - " + response.body().getSubsonicResponse().getError().getMessage()));
                            } else if (response.body().getSubsonicResponse().getStatus().equals(ResponseStatus.OK)) {
                                String password = response.raw().request().url().queryParameter("p");
                                String token = response.raw().request().url().queryParameter("t");
                                String salt = response.raw().request().url().queryParameter("s");
                                callback.onSuccess(password, token, salt);
                            } else {
                                callback.onError(new Exception("Empty response"));
                            }
                        } else {
                            callback.onError(new Exception(String.valueOf(response.code())));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        callback.onError(new Exception(t.getMessage()));
                    }
                });
    }

    public MutableLiveData<SubsonicResponse> ping() {
        MutableLiveData<SubsonicResponse> pingResult = new MutableLiveData<>();

        App.getSubsonicClientInstance(false)
                .getSystemClient()
                .ping()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            pingResult.postValue(response.body().getSubsonicResponse());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        pingResult.postValue(null);
                    }
                });

        return pingResult;
    }

    public MutableLiveData<List<OpenSubsonicExtension>> getOpenSubsonicExtensions() {
        MutableLiveData<List<OpenSubsonicExtension>> extensionsResult = new MutableLiveData<>();

        App.getSubsonicClientInstance(false)
                .getSystemClient()
                .getOpenSubsonicExtensions()
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            extensionsResult.postValue(response.body().getSubsonicResponse().getOpenSubsonicExtensions());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        extensionsResult.postValue(null);
                    }
                });

        return extensionsResult;
    }
}
