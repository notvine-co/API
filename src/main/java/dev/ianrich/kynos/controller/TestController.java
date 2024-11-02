package dev.ianrich.kynos.controller;

import dev.ianrich.kynos.util.ContentUtils;
import lombok.Getter;
import dev.ianrich.kynos.web.construct.Request;
import dev.ianrich.kynos.web.construct.Response;
import dev.ianrich.kynos.web.construct.Route;

import java.io.IOException;

public class TestController {

    @Getter
    private static Route page = (Request request, Response response) -> {
        response.SetContentType("text/html");
        response.withBody(getResponse(request, response));
        response.ok();
        response.flush();
    };

    public static String getResponse(Request request, Response response) throws IOException {
        return ContentUtils.replaceDefaultVariables(request, ContentUtils.getView("default")) + "\nthis is different";
    }
}
