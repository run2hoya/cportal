package com.castis.cportal.repository;

import com.castis.cportal.common.enumeration.BoardType;
import com.castis.cportal.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findTopByBoardTypeAndOwnerId(BoardType type, Integer ownerId);

    Authority findTopByBoardTypeAndAuthoritylListUserId(BoardType type, Integer userId);

}