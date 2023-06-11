/**
 * (C) Copyright 2021 Araf Karsh Hamid 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fusion.water.order.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Function;

import io.fusion.water.order.server.ServiceConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author arafkarsh
 *
 */
@Service
public final class JsonWebToken {
	
	private static String TOKEN = "sigmaEpsilon6109871597";
	
	public static final long EXPIRE_IN_ONE_HOUR 	= 1000 * 60 * 60 * 1;
	public static final long EXPIRE_IN_TWO_HOUR 	= 1000 * 60 * 60 * 2;
	public static final long EXPIRE_IN_THREE_HOUR 	= 1000 * 60 * 60 * 3;
	public static final long EXPIRE_IN_FIVE_HOUR 	= 1000 * 60 * 60 * 5;
	public static final long EXPIRE_IN_EIGHT_HOUR 	= 1000 * 60 * 60 * 8;
	public static final long EXPIRE_IN_ONE_DAY 		= 1000 * 60 * 60 * 24;
	public static final long EXPIRE_IN_TWO_DAYS 	= EXPIRE_IN_ONE_DAY * 2;
	public static final long EXPIRE_IN_ONE_WEEK 	= EXPIRE_IN_ONE_DAY * 7;
	public static final long EXPIRE_IN_TWO_WEEKS 	= EXPIRE_IN_ONE_DAY * 14;
	public static final long EXPIRE_IN_ONE_MONTH 	= EXPIRE_IN_ONE_DAY * 30;
	public static final long EXPIRE_IN_THREE_MONTHS	= EXPIRE_IN_ONE_DAY * 90;
	public static final long EXPIRE_IN_SIX_MONTHS 	= EXPIRE_IN_ONE_DAY * 180;
	public static final long EXPIRE_IN_ONE_YEAR 	= EXPIRE_IN_ONE_DAY * 365;
	public static final long EXPIRE_IN_TWO_YEARS 	= EXPIRE_IN_ONE_YEAR * 2;
	public static final long EXPIRE_IN_FIVE_YEARS 	= EXPIRE_IN_ONE_YEAR * 5;
	public static final long EXPIRE_IN_TEN_YEARS 	= EXPIRE_IN_ONE_YEAR * 10;

	@Autowired
	private ServiceConfiguration serviceConfig;
	
	/**
	 * Returns Token Key - 
	 * In SpringBooT Context from ServiceConfiguration
	 * Else from Static TOKEN Key
	 * @return
	 */
	private String getTokenKey() {
		return (serviceConfig != null) ? serviceConfig.getTokenKey() : TOKEN;
	}
	
    /**
     * Generate Token for the User
     *  
	 * @param _userId
	 * @param _expiryTime
	 * @return
	 */
    public String generateToken(String _userId, long _expiryTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "metarivu");
        claims.put("aud", "ShoppingServices");
        claims.put("jti", UUID.randomUUID().toString());
        return generateToken(_userId,_expiryTime,SignatureAlgorithm.HS512,claims);
    }

    /**
     * Generate Token with Claims
     *  
     * @param _userId
     * @param _expiryTime
     * @param _claims
     * @return
     */
    public String generateToken(String _userId, long _expiryTime,
    		Map<String, Object> _claims) {
        return generateToken(_userId,_expiryTime,SignatureAlgorithm.HS512, _claims);
    }
    
    /**
     * Generate Token with Claims
     * 
     * @param _userId
     * @param _expiryTime
     * @param _algo
     * @param _claims
     * @return
     */
    public String generateToken(String _userId, long _expiryTime,
    		SignatureAlgorithm _algo, Map<String, Object> _claims) {
    	long currentTime = System.currentTimeMillis();
        return Jwts.builder()
        		.setClaims(_claims)
        		.setSubject(_userId)
        		.setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + _expiryTime))
                .signWith(_algo, getTokenKey())
                .compact();
    }

    /**
     * Validate User Id with Token
     * 
     * @param _userId
     * @param _token
     * @return
     */
    public boolean validateToken(String _userId, String _token) {
        return (!isTokenExpired(_token) &&
        		  getSubjectFromToken(_token).equals(_userId));
    }
    
    /**
     * Returns True if the Token is expired
     * 
     * @param _token
     * @return
     */
    public boolean isTokenExpired(String _token) {
        return getExpiryDateFromToken(_token).before(new Date());
    }
    
	/**
	 * Get the User / Subject from the Token
	 * 
	 * @param _token
	 * @return
	 */
    public String getSubjectFromToken(String _token) {
        return getClaimFromToken(_token, Claims::getSubject);
    }

    /**
     * Get the Expiry Date of the Token
     * 
     * @param _token
     * @return
     */
    public Date getExpiryDateFromToken(String _token) {
        return getClaimFromToken(_token, Claims::getExpiration);
    }
    
    /**
     * Token Should not be used before this Date.
     * 
     * @param _token
     * @return
     */
    public Date getNotBeforeDateFromToken(String _token) {
        return getClaimFromToken(_token, Claims::getNotBefore);
    }
    /**
     * Get the Token Issue Date
     * 
     * @param _token
     * @return
     */
    public Date getIssuedAtFromToken(String _token) {
        return getClaimFromToken(_token, Claims::getIssuedAt);
    }
    
    /**
     * Get the Issuer from the Token
     * 
     * @param _token
     * @return
     */
    public String getIssuerFromToken(String _token) {
        return getClaimFromToken(_token, Claims::getIssuer);
    }
    
    /**
     * Get the Audience from the Token
     * 
     * @param _token
     * @return
     */
    public String getAudienceFromToken(String _token) {
        return getClaimFromToken(_token, Claims::getAudience);
    }
    
    /**
     * Return Payload as JSON String
     * 
     * @param _token
     * @return
     */
    public String getPayload(String _token) {
    	StringBuilder sb = new StringBuilder();
		Claims claims = getAllClaims(_token);
		int x=1;
		int size=claims.size();
		sb.append("{");
		for(Entry<String, Object> claim : claims.entrySet()) {
			if(claim != null) {
				sb.append("\""+claim.getKey()+"\": \"").append(claim.getValue());
				sb.append("\"");
				if(x<size) {
					sb.append(",");
				}
			}
			x++;
		}
		sb.append("}");
    	return sb.toString();
    }

    /**
     * Get a Claim from the Token based on the Claim Type
     * 
     * @param <T>
     * @param _token
     * @param _claimsResolver
     * @return
     */
    public <T> T getClaimFromToken(String _token, 
    		Function<Claims, T> _claimsResolver) {
        return _claimsResolver.apply(getAllClaims(_token));
    }
    
    /**
     * Return All Claims for the Token
     * 
     * @param _token
     * @return
     */
    public Claims getAllClaims(String _token) {
        return Jwts
        		.parser()
        		.setSigningKey(getTokenKey())
        		.parseClaimsJws(_token)
        		.getBody();
    }
    
    /**
     * Print Token Stats
     * 
     * @param token
     */
    public static void tokenStats(String token) {
		JsonWebToken jwt = new JsonWebToken();
		System.out.println("----------------------------------------------");
		System.out.println("Token    = "+token);
		System.out.println("----------------------------------------------");

		System.out.println("Subject  = "+jwt.getSubjectFromToken(token));
		System.out.println("Audience = "+jwt.getAudienceFromToken(token));
		System.out.println("Issuer   = "+jwt.getIssuerFromToken(token));
		System.out.println("IssuedAt = "+jwt.getIssuedAtFromToken(token));
		System.out.println("Expiry   = "+jwt.getExpiryDateFromToken(token));
		System.out.println("Expired  = "+jwt.isTokenExpired(token));

		System.out.println("----------------------------------------------");
		Claims claims = jwt.getAllClaims(token);
		int x=1;
		for(Entry<String, Object> o : claims.entrySet()) {
			System.out.println(x+"> "+o);				
			x++;
		}
		System.out.println("----------------------------------------------");
		System.out.println("Payload="+jwt.getPayload(token));
		System.out.println("----------------------------------------------");

    }
    
    /**
     * Returns the Expiry in Days
     * 
     * @param _time
     * @return
     */
    public static double getDays(long _time) {
    	return _time / (1000 * 60 * 60 * 24);
    }
    
    /**
     * Only for Testing from Command Line
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		JsonWebToken jwt = new JsonWebToken();
		
		String subject	 = "araf.karsh";
		long expiry		 = JsonWebToken.EXPIRE_IN_TWO_WEEKS;
		String token	 = jwt.generateToken(subject, expiry);
		
		System.out.println("Expiry Time in Days: "+getDays(expiry));
		tokenStats(token);
	}
}
