package dev.igor.camelaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("accountCode")
    private String accountCode;
    @JsonProperty("userId")
    private String userId;
}
