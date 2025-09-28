package haodo.dev.vn.moneymanager.service;

import haodo.dev.vn.moneymanager.dto.AuthDTO;
import haodo.dev.vn.moneymanager.dto.ProfileDTO;
import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import haodo.dev.vn.moneymanager.mapper.ProfileMapper;
import haodo.dev.vn.moneymanager.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileService {
    ProfileRepository profileRepository;
    EmailService emailService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    ProfileMapper profileMapper;

    public ProfileDTO registerProfile(ProfileDTO profileDTO) {
        ProfileEntity newProfile = profileMapper.toEntity(profileDTO, passwordEncoder);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile.setCreatedAt(LocalDateTime.now());
        newProfile.setUpdatedAt(LocalDateTime.now());
        newProfile = profileRepository.save(newProfile);

        // send activation email
        String activationLink = "http://localhost:8080/activation?token=" + newProfile.getActivationToken();
        String subject = "Activate your Money Manager account";
        String body = "Click on the following link to activate your account: " + activationLink;
        emailService.sendEmail(newProfile.getEmail(), subject, body);

        return profileMapper.toDTO(newProfile);
    }

    public boolean activateProfile(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profile.setUpdatedAt(LocalDateTime.now());
                    profileRepository.save(profile);
                    return true;
                }).orElse(false);
    }

    public boolean isAccountActive(String email) {
        return profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }

    public ProfileEntity getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return profileRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: " + authentication.getName()));
    }

    public ProfileDTO getPublicProfile(String email) {
        ProfileEntity currentUser = null;
        if(email == null) {
            currentUser = getCurrentProfile();
        } else {
            profileRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("Profile not found with email: " + email));
        }

        return profileMapper.toDTO(currentUser);
    }

    public Map<String, Object> authenticateGenerateToken(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
             return Map.of(
                     "token", "JWT token", "user",
                     getPublicProfile(authDTO.getEmail())
             );
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
