package _bbu.lawfirmapi.utils;

import _bbu.lawfirmapi.exceptions.NotFoundException;

import _bbu.lawfirmapi.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@RequiredArgsConstructor
public  class MethodHelper {

    private final JwtService jwtService;
    public void isInvalidPage(Integer totalPages , Integer requestedPage){
        if(requestedPage > totalPages && requestedPage <=0 ){
            throw  new NotFoundException("Page number : " + requestedPage +  " doesn't exist");
        }
    }
    public String extractExpirationDateInCambodia(String token) {
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        Instant instant = expiration.toInstant();
        ZoneId cambodiaZone = ZoneId.of("Asia/Phnom_Penh");
        ZonedDateTime cambodiaTime = instant.atZone(cambodiaZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        return cambodiaTime.format(formatter);
    }


}
