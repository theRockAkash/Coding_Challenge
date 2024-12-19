package com.example.codingchallenge.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.codingchallenge.data.models.NetworkState;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * This class is responsible for monitoring network connection on realtime basis
 * It provides a LiveData which holds the current connection State
 */
public class ConnectivityManagerUtil {

    private final MutableLiveData<NetworkState> _networkState = new MutableLiveData<>();
    public final LiveData<NetworkState> networkState = _networkState;
    private final ConnectivityManager connectivityManager;

    @Inject
    public ConnectivityManagerUtil(@ApplicationContext Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    // Start monitoring network connectivity
    public void startNetworkMonitoring() {
        //This flag will be used to monitor the case when activity is first started and network
        // is connected, don't show that network is connected, show only only when
        // network becomes available after lost.
        AtomicBoolean isNetworkLost = new AtomicBoolean(false);

        //Case when network is not available initially
        if(!isNetworkConnected()){
            _networkState.postValue(NetworkState.DISCONNECTED);
            isNetworkLost.set(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // API >= 24 (Android N) for better network callback support
            connectivityManager.registerDefaultNetworkCallback(new android.net.ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    if (isNetworkLost.get()) {
                        _networkState.postValue(NetworkState.CONNECTED);
                    }
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    isNetworkLost.set(true);
                    _networkState.postValue(NetworkState.DISCONNECTED);
                }
            });
        } else {
            // For devices below API 24, we will use legacy API
            android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (isNetworkLost.get()) {
                    _networkState.postValue(NetworkState.CONNECTED);
                }
            } else {
                isNetworkLost.set(true);
                _networkState.postValue(NetworkState.DISCONNECTED);
            }
        }
    }

    /**
     * Provides one time check network check functionality
     * can be used before calling APIService
     * @return
     */
    public boolean isNetworkConnected() {
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            if (capabilities != null) {
                // Check if network has internet capability
                return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        return false;
    }

}
