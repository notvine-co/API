package dev.ianrich.kynos.controller.error.get;

import dev.ianrich.kynos.model.Profile;
import dev.ianrich.kynos.profile.ProfileHandler;
import dev.ianrich.kynos.util.ContentUtils;
import dev.ianrich.kynos.web.construct.Request;
import dev.ianrich.kynos.web.construct.Response;
import dev.ianrich.kynos.web.construct.Route;
import lombok.Getter;

import java.io.IOException;

public class GetErrorPage {

    @Getter
    private static Route page = (Request request, Response response) -> {

        response.SetContentType("text/html");
        response.withBody(getResponse(request, response));
        response.ok();
        response.flush();

    };

    public static String getResponse(Request request, Response response) throws IOException {
        return ContentUtils.replaceImageVariables(ContentUtils.getView("all/navbar") + ContentUtils.getView("error/error"));
    }

}
