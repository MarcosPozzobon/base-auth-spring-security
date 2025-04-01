package com.marcos.desenvolvimento.security.services;


import com.marcos.desenvolvimento.usecases.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserUseCase userUseCase;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String loginOrEmail) throws UsernameNotFoundException {
        final var user = userUseCase.byLogin(loginOrEmail);

        final var authorityListAdmin = AuthorityUtils.createAuthorityList("USER", "ADMIN");
        final var authorityListUser = AuthorityUtils.createAuthorityList("USER");

        return UserDetailsImpl.build(user, Boolean.TRUE.equals(user.getAdmin()) ? authorityListAdmin : authorityListUser);
    }
}
