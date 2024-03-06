package com.aciad.chatime.screens.otp;

import static com.aciad.chatime.utils.Constants.FIRST_LOGIN;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.aciad.chatime.R;
import com.aciad.chatime.databinding.FragmentOtpAuthBinding;
import com.aciad.chatime.dialogs.ErrorDialogFragment;
import com.aciad.chatime.dialogs.LoadingDialogFragment;
import com.aciad.chatime.utils.SharedPrefsUtil;

public class OtpAuthFragment extends Fragment {
    private FragmentOtpAuthBinding binding;
    private LoadingDialogFragment loadingDialogFragment;
    private OtpViewModel viewModel;
    private NavController navController;
    private final OtpViewModel.OnResultCallback onResultCallback = new OtpViewModel.OnResultCallback() {
        @Override
        public void onCodeSent() {}

        @Override
        public void onSignIn() {
            loadingDialogFragment.dismiss();
            sendAgainCountDown.cancel();
            boolean isFirstLogin = SharedPrefsUtil.getInstance().getBooleanFromSP(FIRST_LOGIN, true);
            navController.navigate(isFirstLogin ? R.id.action_otp_auth_dest_to_splash_dest : R.id.action_profile_dest_to_splash_dest);
        }

        @Override
        public void onFailure(String errorMessage) {
            binding.authEdtOtp.setText("");
            loadingDialogFragment.dismiss();
            new ErrorDialogFragment(errorMessage).show(getChildFragmentManager(), ErrorDialogFragment.TAG);
        }
    };
    private final CountDownTimer sendAgainCountDown = new CountDownTimer(30000L, 1000L) {
        @Override
        public void onTick(long millisUntilFinished) {
            binding.authTxtResend.setText(requireContext().getString(R.string.resend_code_text, String.valueOf(millisUntilFinished / 1000)));
        }

        @Override
        public void onFinish() {
            binding.authTxtResend.setVisibility(View.INVISIBLE);
            binding.authBtnResend.setEnabled(true);
        }
    };
    private final TextWatcher codeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 6) {
                loadingDialogFragment.show(getChildFragmentManager(), LoadingDialogFragment.TAG);
                viewModel.signInWithPhoneAuthCredential(viewModel.buildCredential(s.toString()), requireActivity());
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(OtpViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOtpAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        loadingDialogFragment = new LoadingDialogFragment();
        viewModel.setOnResultCallback(onResultCallback);
        sendAgainCountDown.start();
        initViews();
    }

    private void initViews() {
        binding.authEdtOtp.addTextChangedListener(codeTextWatcher);
        binding.authBtnResend.setOnClickListener(v -> onResendClicked());
    }

    private void onResendClicked() {
        sendAgainCountDown.start();
        binding.authTxtResend.setVisibility(View.VISIBLE);
        binding.authBtnResend.setEnabled(false);
        viewModel.resendCode(requireActivity());
    }
}