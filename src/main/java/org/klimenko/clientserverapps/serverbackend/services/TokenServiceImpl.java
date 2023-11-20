package org.klimenko.clientserverapps.serverbackend.services;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import org.klimenko.clientserverapps.serverbackend.models.Token;
import org.klimenko.clientserverapps.serverbackend.models.User;

@Component
public class TokenServiceImpl implements TokenService {

    private static final String ISSUER = "ru.mirea.ksp.ds";
    private static final String SIGNING_KEY = "kjhkjsdhf^%76*&^usadhkjasd";
    private static final String AUDIENCE = "Users";

    /**
     * Creates a json web token which is a digitally signed token that contains a payload (e.g. userId to identify
     * the user). The signing key is secret. That ensures that the token is authentic and has not been modified.
     * Using a jwt eliminates the need to store authentication session information in a database.
     * @param user
     * @param durationDays
     * @return
     */
    @Override
    public String createJWT(User user, long durationDays) throws RuntimeException
    {
        //Current time and signing algorithm
        Calendar cal = Calendar.getInstance();
        HmacSHA256Signer signer;
        try {
            signer = new HmacSHA256Signer(ISSUER, null, SIGNING_KEY.getBytes());
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
        token.setAudience(AUDIENCE);
        token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
        token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + 1000L * 60L * 60L * 24L * durationDays));

        JsonObject request = new JsonObject();
        request.addProperty("userId", user.getUserID());
        request.addProperty("name", user.getName());


        JsonObject payload = token.getPayloadAsJsonObject();
        payload.add("info", request);
        try {
            return token.serializeAndSign();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Token verifyToken(String token)
    {
        try {
            final Verifier hmacVerifier = new HmacSHA256Verifier(SIGNING_KEY.getBytes());
            VerifierProvider hmacLocator = new VerifierProvider() {
                @Override
                public List<Verifier> findVerifier(String s, String s1) {
                    return Lists.newArrayList(hmacVerifier);
                }
            };
            VerifierProviders locators = new VerifierProviders();
            locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);
            net.oauth.jsontoken.Checker checker = new net.oauth.jsontoken.Checker(){
                @Override
                public void check(JsonObject payload) throws SignatureException {
                    // don't throw - allow anything
                }
            };
            //Ignore Audience does not mean that the Signature is ignored
            JsonTokenParser parser = new JsonTokenParser(locators, checker);
            JsonToken jt;
            try {
                jt = parser.verifyAndDeserialize(token);
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }
            JsonObject payload = jt.getPayloadAsJsonObject();
            Token t = new Token();
            String issuer = payload.getAsJsonPrimitive("iss").getAsString();
            String userID =  payload.getAsJsonObject("info").getAsJsonPrimitive("userId").getAsString();
            String userName = payload.getAsJsonObject("info").getAsJsonPrimitive("name").getAsString();
            if (issuer.equals(ISSUER) && !StringUtils.isBlank(userID))
            {
                t.setUserId(Integer.parseInt(userID));
                t.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
                t.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
                return t;
            }
            else
            {
                return null;
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
