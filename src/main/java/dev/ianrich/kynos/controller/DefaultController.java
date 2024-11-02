package dev.ianrich.kynos.controller;

import dev.ianrich.kynos.util.ContentUtils;
import lombok.Getter;
import dev.ianrich.kynos.web.construct.Request;
import dev.ianrich.kynos.web.construct.Response;
import dev.ianrich.kynos.web.construct.Route;

public class DefaultController {
    @Getter
    private static Route page = (Request request, Response response) -> {
        response.SetContentType("text/html");
        response.withBody(ContentUtils.getView("default"));
        response.ok();
        response.flush();
    };
}