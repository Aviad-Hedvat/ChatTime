package com.aciad.chatime.screens.splash;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.aciad.chatime.R;
import com.aciad.chatime.data.firebase.DatabaseRepository;
import com.aciad.chatime.model.User;
import com.aciad.chatime.utils.Constants;
import com.aciad.chatime.utils.SharedPrefsUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SplashViewModel extends ViewModel {
    private final static String TAG = "SplashViewModel";
    private final FirebaseAuth mAuth;
    private final DatabaseRepository repository;

    @Inject
    public SplashViewModel(FirebaseAuth mAuth, DatabaseRepository repository) {
        this.mAuth = mAuth;
        this.repository = repository;
    }

    public void handleNavigation(NavController navController) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mAuth.getCurrentUser() != null) {
                getUserAndNavigate(mAuth.getCurrentUser(), navController);
            } else {
                navController.navigate(R.id.action_splash_dest_to_otp_send_fragment);
            }
        }, 2000L);
    }

    private void getUserAndNavigate(FirebaseUser currentUser, NavController navController) {
        repository.getUser(currentUser.getUid(), new DatabaseRepository.OnUserDataChangedListener() {
            @Override
            public void onUserDataChanged(User user) {
                SharedPrefsUtil.getInstance().putObject(Constants.USER, user);
                SharedPrefsUtil.getInstance().putBooleanToSP(Constants.FIRST_LOGIN, user.isFirstLogin());

                if (user.isFirstLogin()) {
                    navController.navigate(R.id.action_splash_dest_to_profile_dest);
                } else {
                    navController.navigate(R.id.action_splash_dest_to_main_fragment);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, e.getMessage());
            }
        });
    }
}
