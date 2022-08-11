package com.castis.cportal.service;

import com.castis.cportal.common.enumeration.BoardType;
import com.castis.cportal.model.Authority;
import com.castis.cportal.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public void save(Authority authority) { authorityRepository.save(authority); }

    public Authority getAuthority(long authorityId) { return authorityRepository.findOne(authorityId);}


    public Authority getAuthority(BoardType type, Integer ownerId) {
        return authorityRepository.findTopByBoardTypeAndOwnerId(type, ownerId);
    }

    public Authority getAuthorityByUserID(BoardType type, Integer userId) {
        return authorityRepository.findTopByBoardTypeAndAuthoritylListUserId(type, userId);
    }

}
