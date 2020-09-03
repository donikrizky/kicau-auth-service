package com.donikrizky.kicau.authservice.config.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String currentEmail = "system";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            currentEmail = ((UserPrincipal) authentication.getPrincipal()).getName();
//        }

        return Optional.of(currentEmail);
    }

}
