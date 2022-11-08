package com.increff.pos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

    @Value("${app.baseUrl}")
    private String baseUrl;

    @RequestMapping(value = "/ui")
    public ModelAndView index() {
        return mav("index.html");
    }

    @RequestMapping(value = "/ui/brands")
    public ModelAndView brand() {
        return mav("brand.html");
    }

    @RequestMapping(value = "/ui/products")
    public ModelAndView product() {
        return mav("product.html");
    }

    @RequestMapping(value = "/ui/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/ui/orders")
    public ModelAndView order() {
        return mav("order.html");
    }

    @RequestMapping(value = "/ui/reports")
    public ModelAndView report() {
        return mav("reports.html");
    }

    private ModelAndView mav(String page) {

        ModelAndView mav = new ModelAndView(page);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

}
