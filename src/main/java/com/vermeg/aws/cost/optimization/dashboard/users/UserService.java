package com.vermeg.aws.cost.optimization.dashboard.users;

import com.vermeg.aws.cost.optimization.dashboard.auth.AuthenticationResponse;
import com.vermeg.aws.cost.optimization.dashboard.auth.AuthenticationService;
import com.vermeg.aws.cost.optimization.dashboard.auth.RegisterRequest;
import com.vermeg.aws.cost.optimization.dashboard.config.JwtService;
import com.vermeg.aws.cost.optimization.dashboard.mfa.TwoFactorAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TwoFactorAuthenticationService tfaService;
    private final AuthenticationService authenticationService;

    public AuthenticationResponse addUser(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .mfaEnabled(false)
                .secret(null)
                .build();

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authenticationService.saveUserToken(jwtToken, savedUser);
        return AuthenticationResponse.builder()
                .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    public User getUser(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void resetPassword(String email, String newPassword) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        var jwtToken = jwtService.generateToken(user);
        authenticationService.revokeAllUserTokens(user);
        authenticationService.saveUserToken(jwtToken, user);
        // Save the updated user entity
        repository.save(user);
    }
}
