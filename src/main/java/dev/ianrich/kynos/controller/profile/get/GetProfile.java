package dev.ianrich.kynos.controller.profile.get;

import dev.ianrich.kynos.model.Profile;
import dev.ianrich.kynos.profile.ProfileHandler;
import dev.ianrich.kynos.util.ContentUtils;
import dev.ianrich.kynos.web.construct.Request;
import dev.ianrich.kynos.web.construct.Response;
import dev.ianrich.kynos.web.construct.Route;
import lombok.Getter;

import java.io.IOException;

public class GetProfile {

    @Getter
    private static Route page = (Request request, Response response) -> {

        if(!request.getParameters().containsKey("id")){
            response.SetContentType("text/html");
            response.withBody("You are missing a parameter. Make sure you have an id!");
            response.ok();
            response.flush();
            return;
        }

        response.SetContentType("text/html");
        response.withBody(getResponse(request, response));
        response.ok();
        response.flush();

    };

    public static String getResponse(Request request, Response response) throws IOException {
        Profile profile = ProfileHandler.getByUsername(request.getParameters().get("id"));

        return ContentUtils.replaceDefaultVariables(request, ContentUtils.getView("profile/view"))
                .replace("%username%", profile.getUsername())
                .replace("%password%", profile.getPassword())
                .replace("%avatar%", profile.getAvatar());
    }

}
