package com.increff.pos.dto.dtoHelper;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.DataUI.ProductDataUI;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductDtoHelper {


    public static ProductPojo convertProductFormtoProductPojo(ProductForm productForm, Integer brandCategoryId)
    {
        ProductPojo productPojo=new ProductPojo();
        productPojo.setName(productForm.getName());
        productPojo.setBrandCategoryId(brandCategoryId);
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setMrp(productForm.getMrp());
        return productPojo;

    }

    public static ProductData convertProductPojotoProductData(ProductPojo productPojo, BrandPojo brandPojo)
    {
        ProductData productData=new ProductData();
        productData.setId(productPojo.getId());
        productData.setBrand(brandPojo.getBrand());
        productData.setCategory(brandPojo.getCategory());
        productData.setBarcode(productPojo.getBarcode());
        productData.setMrp(productPojo.getMrp());
        productData.setName(productPojo.getName());
        return productData;
    }

    public static void validateBarcode(String barcode)throws ApiException
    {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
        Matcher matcher = pattern.matcher(barcode);
        if(!matcher.find())
        {
            throw new ApiException("barcode " + barcode + " not valid,barcode can only have alphanumerc values");
        }
    }

    public static Boolean validateBarcodeBulk(String barcode)
    {
        Pattern pattern = Pattern.compile("^[0-9A-Za-z]+$");
        Matcher matcher = pattern.matcher(barcode);
        return matcher.find();
    }

    public static void validateMRP(Double mrp)throws ApiException
    {
        Pattern pattern = Pattern.compile("^[0-9]+$|^[0-9]+\\.[0-9]*$");
        Matcher matcher=pattern.matcher(mrp.toString());
        if(!matcher.find())
        {
            throw new ApiException("Mrp " + mrp + "not vlaid, mrp should be a Positive Nume=ber");
        }
    }

    public static Boolean validateMRPBulk(Double mrp)throws ApiException
    {
        Pattern pattern = Pattern.compile("^[0-9]+$|[0-9]+\\.[0-9]*$");
        Matcher matcher = pattern.matcher(mrp.toString());
        return matcher.find();
    }

    public static void checkDuplicates(List<ProductForm> productFormList)
    {
        Set<String> barcodeSet=new HashSet<>();
        List<String> errorList=new ArrayList<>();
        Integer row=1;
        for(ProductForm productForm:productFormList)
        {
            if(barcodeSet.contains(productForm.getBarcode()))
            {
                errorList.add("Error : row = "+ row + "Barcode should not be repeated, barcode = "+productForm.getBarcode());
            }
            else
            {
                barcodeSet.add(productForm.getBarcode());
            }
            row++;
        }
    }

    public static ProductDataUI convertProductFormtoProductDataUI(ProductForm productForm)
    {
        ProductDataUI productDataUI = new ProductDataUI();
        productDataUI.setBarcode(productForm.getBarcode());
        productDataUI.setBrand(productForm.getBrand());
        productDataUI.setName(productForm.getName());
        productDataUI.setCategory(productForm.getCategory());
        productDataUI.setMrp(productForm.getMrp());
        return productDataUI;
    }


}
