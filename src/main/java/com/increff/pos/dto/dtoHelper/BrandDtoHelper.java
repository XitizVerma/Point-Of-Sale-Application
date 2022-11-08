package com.increff.pos.dto.dtoHelper;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.DataUI.BrandDataUI;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.pos.util.DataUtil.checkNotNullBulkUtil;
import static com.increff.pos.util.ErrorUtil.throwError;

public class BrandDtoHelper {

    //Static Conversions
    public static BrandData convertBrandPojoToBrandData(BrandPojo brandPojo) {
        BrandData brandData = new BrandData();
        brandData.setId(brandPojo.getId());
        brandData.setBrand(brandPojo.getBrand());
        brandData.setCategory(brandPojo.getCategory());
        return brandData;

    }

    public static BrandPojo convertBrandFormtoBrandPojo(BrandForm brandForm) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());
        return brandPojo;
    }


    public static List<BrandPojo> convertBrandFormtoBrandPojoList(List<BrandForm> brandFormList){
        List<BrandPojo> brandPojoList = new ArrayList<>();
        for (BrandForm brandForm : brandFormList) {
            brandPojoList.add(convertBrandFormtoBrandPojo(brandForm));
        }
        return brandPojoList;
    }

    public static BrandPojo convertBrandDatatoBrandPojo(BrandData brandData) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setId(brandData.getId());
        brandPojo.setBrand(brandData.getBrand());
        brandPojo.setCategory(brandData.getCategory());
        return brandPojo;
    }

    public static void validateList(List<BrandForm> brandFormList) throws ApiException {
        List<String> errorList = new ArrayList<>();
        Integer row = 1;
        for (BrandForm brandForm : brandFormList) {
            if (!checkNotNullBulkUtil(brandForm)) {
                errorList.add("Error : row ->" + (row) + "brand or category cannot be empty");
            }
            row++;
        }
        if (errorList.size() > 0) {
            String errorStr = "";
            for (String e : errorList) {
                errorStr += e + "\n";
            }
            throw new ApiException(errorStr);
        }
    }


    public static void checkDuplicates(List<BrandForm> brandFormList) throws ApiException {

        Set<String> brandSet = new HashSet<>();
        List<String> errorList = new ArrayList<>();
        Integer row = 1;
        for (BrandForm brandForm : brandFormList) {
            if (brandSet.contains(brandForm.getBrand() + brandForm.getCategory())) {
                errorList.add("Error : row ->" + (row) + "Brand-Category should not be repeated, BrandCategory : " + brandForm.getBrand() + "-" + brandForm.getCategory());
            } else {
                brandSet.add(brandForm.getBrand() + brandForm.getCategory());
            }
            row++;
        }
        if (!CollectionUtils.isEmpty(errorList)) {
            throwError(errorList);
        }
    }

    public static BrandDataUI convertBrandFormtoBrandDataUI(BrandForm brandForm)
    {
        BrandDataUI brandDataUI=new BrandDataUI();
        brandDataUI.setBrand(brandForm.getBrand());
        brandDataUI.setCategory(brandForm.getCategory());
        return brandDataUI;

    }
}