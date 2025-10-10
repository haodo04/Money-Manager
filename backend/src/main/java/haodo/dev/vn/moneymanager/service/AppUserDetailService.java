package haodo.dev.vn.moneymanager.service;

import haodo.dev.vn.moneymanager.entity.ProfileEntity;
import haodo.dev.vn.moneymanager.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppUserDetailService implements UserDetailsService {
    ProfileRepository profileRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity existingProfile = profileRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Profile not found with email"));
        return User.builder()
                .username(existingProfile.getEmail())
                .password(existingProfile.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
