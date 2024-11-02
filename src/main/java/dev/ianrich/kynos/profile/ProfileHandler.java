package dev.ianrich.kynos.profile;

import dev.ianrich.kynos.model.Profile;
import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
public class ProfileHandler {
    public static ArrayList<Profile> profiles;

    public ProfileHandler(){
        profiles = new ArrayList<>();
    }

    public static Profile getByUsername(String username){
        if(profiles.stream().anyMatch(u -> username.equalsIgnoreCase(username))) return profiles.stream().filter(u -> username.equalsIgnoreCase(username)).collect(Collectors.toList()).get(0);
        return null;
    }
}
