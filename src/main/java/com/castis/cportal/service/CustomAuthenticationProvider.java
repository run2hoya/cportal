package com.castis.cportal.service;

import java.util.Collection;

import com.castis.cportal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.idgenerator.IdGenerator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;	

    @Autowired
	private UserService userService;
     
   
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String userId = authentication.getName();
        String password = (String) authentication.getCredentials();
  
        User user = null;
        Collection<GrantedAuthority> authorities = null;
        UsernamePasswordAuthenticationToken result = null;
        
        try {
        	
        	Object details = authentication.getDetails();            
            String remoteAddress = null;
            if (details instanceof WebAuthenticationDetails) {
                remoteAddress = ((WebAuthenticationDetails) details).getRemoteAddress(); 
            }
            
    		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
    		StringBuilder logString = new StringBuilder(trId + ">>> " + "[Login GetUser]");
    		logString.append("clientAddr ["+ remoteAddress +"], ");    		    		
    		log.info(logString.toString());
            
            user = (User)userService.getUserByUserID(userId);
  
            if(user == null)
				throw new UsernameNotFoundException("User 정보를 찾을 수 없음");
            
            log.info(user.toString());
            // 이용자가 로그인 폼에서 입력한 비밀번호와 DB로부터 가져온 암호화된 비밀번호를 비교한다                        			
            
            if(user.isEnabled() == false)
				throw new BadCredentialsException("사용 할 수 없는 계정입니다.");
            
            if (!passwordEncoder.matches(password, user.getPassword()))
            	throw new BadCredentialsException("비밀번호 불일치");
  
            authorities = user.getAuthorities();
            
            result = new UsernamePasswordAuthenticationToken(userId, password, authorities);
            result.setDetails(user);
            
        } catch(UsernameNotFoundException e) {
        	log.error("" + e);
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
        	log.error("" + e);
            throw new BadCredentialsException(e.getMessage());
        } catch(Exception e) {
        	log.error("" + e);
            throw new RuntimeException(e.getMessage());
        }
  
        return result;
    }
 
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
