package com.genesislabs.config.security;

import com.genesislabs.video.service.CustomUserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    CustomUserDetailService userDetailsService;

    @Override
    public Authentication authenticate(Authentication _auth) throws AuthenticationException {
        String email = (String) _auth.getPrincipal();
        String password = (String) _auth.getCredentials();
        User user = (User) userDetailsService.loadUserByUsername(email);

        if(!user.getPassword().equals(password))
            throw new BadCredentialsException("'" + email + "' Invalid password");

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    private static String getSHA256Value(String _strSource) {
        String strResult = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(_strSource.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++)
                sb.append(Integer.toString( (byteData[i]&0xff) + 0x100, 16).substring(1) );
            strResult = sb.toString().toUpperCase();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            strResult = null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return strResult;
    }

}
