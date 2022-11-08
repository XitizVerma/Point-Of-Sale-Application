package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.DataUI.ProductDataUI;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.dto.dtoHelper.ProductDtoHelper.*;
import static com.increff.pos.util.DataUtil.*;
import static com.increff.pos.util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

@Service
public class ProductDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    public ProductDataUI add(ProductForm productForm) throws ApiException {
        validateProductForm(productForm);
        productForm = normalize(productForm);
        Integer brandId = getBrandIdByBrandCategory(productForm.getBrand(), productForm.getCategory());
        ProductPojo productPojo=convertProductFormtoProductPojo(productForm, brandId);
        productPojo.setBrandCategoryId(brandId);
        productService.add(productPojo);
        return convertProductFormtoProductDataUI(productForm);
    }

    public Integer bulkAdd(List<ProductForm> productFormList)throws ApiException
    {
        if(CollectionUtils.isEmpty(productFormList))
        {
            throw new ApiException("Empty Data");
        }
        validateProductList(productFormList);
        List<ProductPojo> productPojoList=new ArrayList<>();
        for(ProductForm productForm : productFormList)
        {
            Integer brandId=getBrandIdByBrandCategory(productForm.getBrand(), productForm.getCategory());
            ProductPojo productPojo=convertProductFormtoProductPojo(productForm, brandId);
            productPojo.setBrandCategoryId(brandId);
            productPojoList.add(productPojo);
        }

        productService.bulkAdd(productPojoList);
        return productFormList.size();
    }

    public ProductData get(Integer id)throws ApiException{
        ProductPojo productPojo = productService.get(id);
        BrandPojo brandPojo = brandService.get(productPojo.getBrandCategoryId());
        return convertProductPojotoProductData(productPojo, brandPojo);
    }
    public List<ProductData> getAll()throws ApiException{
        List<ProductPojo> productPojoList= productService.getAll();
        List<ProductData> productDataList=new ArrayList<>();
        for(ProductPojo productPojo:productPojoList)
        {
            BrandPojo brandPojo = brandService.get(productPojo.getBrandCategoryId());
            productDataList.add(convertProductPojotoProductData(productPojo, brandPojo));
        }
        return productDataList;
    }

    public ProductDataUI update(ProductForm productForm)throws ApiException
    {
        validateProductForm(productForm);
        productForm=normalize(productForm);
        ProductPojo productPojo=productService.selectByBarcode(productForm.getBarcode());
        BrandPojo brandPojo = brandService.selectCheckByBrandCategory(productForm.getBrand(), productForm.getCategory());

       // Integer brandId=getBrandIdByBrandCategory(productUpdateForm.getBrand(),productUpdateForm.getCategory());
        ProductPojo productPojoConverted = convertProductFormtoProductPojo(productForm, brandPojo.getId());
        productService.update(productPojoConverted);
        return convertProductFormtoProductDataUI(productForm);
    }

    public Integer getBrandIdByBrandCategory(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandService.selectByBrandCategory(brand, category);
        if (isNull(brandPojo)) {
            throw new ApiException(brand + " - " + category + "Brand-Category Combination does not exist");
        }
        return brandPojo.getId();
    }

    public void validateProductList(List<ProductForm>productFormList)throws ApiException{
        checkDuplicates(productFormList);
        List<String> errorList=new ArrayList<>();
        Integer row=1;
        for(ProductForm productForm:productFormList)
        {
            normalize(productForm);
            if(!checkNotNullBulkUtil(productForm))
            {
                errorList.add("Error : row = " + row + "Parameters in the insert form cannot be null");
                continue;
            }
            if(!validateMRPBulk(productForm.getMrp()))
            {
                errorList.add("Error : row = "+row+" mrp "+productForm.getMrp()+" aint valid, Positive Number it should be");
            }
            if(!validateBarcodeBulk(productForm.getBarcode()))
            {
                errorList.add("Error : row = "+row+ " barcode "+productForm.getBarcode()+" aint valid, Barcode can have only Alphanumeric Values valid by regex");
            }
            if(!isNull(productService.selectByBarcode(productForm.getBarcode())))
            {
                errorList.add("Error : row : =" + row + " barcode " + productForm.getBarcode()+"already Exists");
            }
            BrandPojo brandPojo=brandService.selectByBrandCategory(productForm.getBrand(),productForm.getCategory());
            if(isNull(brandPojo))
            {
                errorList.add("Error : row = " + row + " "+productForm.getBrand()+" - "+productForm.getCategory()+"Brand Categoty doesnt exist");
            }
            row++;
        }
        if(CollectionUtils.isEmpty(errorList))//TODO is EMpty research !
        {
            throwError(errorList);
        }
    }

    public void validateProductForm(ProductForm productForm)throws ApiException
    {
        if(isNull(productForm.getBrand()) || productForm.getBrand().trim().isEmpty())
        {
            throw new ApiException("Brand cannot be Empty!");
        }
        if(isNull(productForm.getCategory()) || productForm.getCategory().trim().isEmpty())
        {
            throw new ApiException("Category cannot be Empty!");
        }
        if(isNull(productForm.getName()) || productForm.getName().trim().isEmpty())
        {
            throw new ApiException("Product Name cannot be Empty!");
        }
        if(isNull(productForm.getBarcode()) || productForm.getBarcode().trim().isEmpty())
        {
            throw new ApiException("Barcode cannot be Empty!");
        }
        if(isNull(productForm.getMrp()) || productForm.getMrp()<=0)
        {
            throw new ApiException("Kindly Enter valid Mrp");
        }
    }
    public ProductForm normalize(ProductForm productForm)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        productForm.setName(productForm.getName().trim().toLowerCase());
        productForm.setBrand(productForm.getBrand().trim().toLowerCase());
        productForm.setCategory(productForm.getCategory().trim().toLowerCase());
        productForm.setBarcode(productForm.getBarcode().trim());
        productForm.setMrp(Double.parseDouble(df.format(productForm.getMrp())));
        return productForm;
    }




}
