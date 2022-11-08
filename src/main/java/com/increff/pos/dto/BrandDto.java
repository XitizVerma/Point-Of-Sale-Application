package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.DataUI.BrandDataUI;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.pos.dto.dtoHelper.BrandDtoHelper.*;
import static java.util.Objects.isNull;

@Service
public class BrandDto {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    public List<BrandData> getAll() throws ApiException {
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandData> brandDataList = new ArrayList<>();
        for (BrandPojo brandPojo : brandPojoList) {
            brandDataList.add(convertBrandPojoToBrandData(brandPojo));
        }
        return brandDataList;
    }

    public BrandDataUI add(BrandForm brandForm) throws ApiException
    {
        validateBrandForm(brandForm);
        brandForm=normalize(brandForm);
        brandService.add(convertBrandFormtoBrandPojo(brandForm));
        return convertBrandFormtoBrandDataUI(brandForm);
    }

    public Integer bulkAdd(List<BrandForm> brandFormList) throws ApiException {
        if (brandFormList.size() == 0)
            throw new ApiException("Empty Data");

        validateList(brandFormList);
        checkDuplicates(brandFormList);

        brandService.bulkAdd(convertBrandFormtoBrandPojoList(brandFormList));

        return brandFormList.size();

    }

    public BrandData get(Integer id) throws ApiException {
        return convertBrandPojoToBrandData(brandService.get(id));
    }

    public BrandDataUI update(BrandForm brandForm,Integer id) throws ApiException
    {
        if (CollectionUtils.isEmpty(productService.selectByBrandId(id))==false)
        {
            throw new ApiException("Cannot Update " + brandForm.getBrand() + " - " + brandForm.getCategory() + " as product for this exists");
        }
        validateBrandForm(brandForm);
        brandForm=normalize(brandForm);
        brandService.update(convertBrandFormtoBrandPojo(brandForm),id);
        return convertBrandFormtoBrandDataUI(brandForm);
    }

    public void validateBrandForm(BrandForm brandForm)throws ApiException
    {
        if(isNull(brandForm.getBrand()) || brandForm.getBrand().isEmpty())
        {
            throw new ApiException("Brand cannot be Empty!");
        }
        if(isNull(brandForm.getCategory()) || brandForm.getCategory().isEmpty())
        {
            throw new ApiException("Category cannot be Empty!");
        }
    }

    public BrandForm normalize(BrandForm brandForm){
        brandForm.setBrand(brandForm.getBrand().trim().toLowerCase());
        brandForm.setCategory(brandForm.getCategory().trim().toLowerCase());
        return brandForm;
    }

}