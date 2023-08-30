package com.lasindu.clothfy_store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResDTO {
    /*
     * use this token to access endpoints
     * send as a Bearer token in request header -> Authorization
     */
    @JsonProperty("access_token")
    private String accessToken;

    /*
     * this token have longer expiration time.
     * use this to refresh access token without asking for username and password
     * send as a Bearer token in request header -> Authorization
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
