package com.lastdance.beeper.data.domain;

import com.lastdance.beeper.data.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(force = true)
@ToString
@Entity(name = "user")
public class User extends Base implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "nickname",nullable = false, length = 45)
    private String nickname;

    @Column(columnDefinition = "DATE", nullable = false)
    private Date birthDate;

    @Column(name = "phone_number", columnDefinition = "TEXT", nullable = false)
    private String phoneNumber;

    @Column(name = "password",nullable = false, columnDefinition="TEXT" )
    private String password;

    @Column(name = "point", nullable = false)
    private Long point;

    @Column(name = "image_url",columnDefinition="TEXT") //image default null
    private String image;

    //알람 동의 여부
    @Column(name = "alarm_status", nullable = false)
    private Boolean alarmStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Builder
    public User(
            String phoneNumber,
            Date birthDate,
            String password,
            String nickname,
            String image,
            Boolean alarmStatus,
            List<String> roles,
            Long point
    )
    {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
        this.roles = roles;
        this.alarmStatus = alarmStatus;
        this.point = point;
        this.birthDate = birthDate;
    }

    public void update(UserDTO.RequestForUpdate updateDTO, String image){
        this.phoneNumber = updateDTO.getPhoneNumber();
        this.nickname = updateDTO.getNickname();
        this.image = image;
    }

    public void updatePasswordKey(String passwordKey){
        this.password = passwordKey;
    }

    public void updateFlag(Boolean alarmStatus){
        this.alarmStatus = alarmStatus;
    }


    //jwt token setting
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
