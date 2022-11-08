package com.increff.pos.controller;


import com.increff.pos.dto.BrandDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.DataUI.BrandDataUI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO :REFACTOR CODE
@Api
@RequestMapping(path="/brands")
@RestController
public class BrandController {

    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value = "Gets all Brand Details")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<BrandData> getAllBrandDetails() throws ApiException {
        return brandDto.getAll();
    }

    @ApiOperation(value = "Adds Brand Details")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public BrandDataUI insertBrand(@RequestBody BrandForm brandForm) throws ApiException {
        return brandDto.add(brandForm);
    }

    @ApiOperation(value = "Adds Brand Details in bulk")
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public Integer bulkInsertBrand(@RequestBody List<BrandForm> brandFormList) throws ApiException {
        return brandDto.bulkAdd(brandFormList);
    }

    @ApiOperation(value = "Get a Brand Detail")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public BrandData getBrand(@PathVariable Integer id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value = "Updates a Brand")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public BrandDataUI updateBrand(@RequestBody BrandForm brandForm, @PathVariable Integer id) throws ApiException {
        return brandDto.update(brandForm,id);
    }


}