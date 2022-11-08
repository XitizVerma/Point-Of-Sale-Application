package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.DataUI.InventoryDataUI;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.InventoryReport;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.pos.dto.dtoHelper.InventoryDtoHelper.*;
import static com.increff.pos.util.DataUtil.checkNotNullBulkUtil;
import static com.increff.pos.util.ErrorUtil.throwError;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    public InventoryData get(String barcode)throws ApiException
    {
        return convertInventoryPojotoInventoryData(inventoryService.selectByBarcode(barcode));
    }

    public List<InventoryData> getAll()throws ApiException
    {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList)
        {
            inventoryDataList.add(convertInventoryPojotoInventoryData(inventoryPojo));
        }
        return inventoryDataList;
    }

    public InventoryDataUI add(InventoryForm inventoryForm)throws ApiException
    {
        validateInventoryForm(inventoryForm);
        inventoryForm=normalize(inventoryForm);
        InventoryPojo inventoryPojo = convertInventoryFormtoInventoryPojo(inventoryForm);
        validateBarcode(inventoryPojo);
        inventoryService.add(inventoryPojo);
        return convertInventoryFormtoInventoryDataUI(inventoryForm);
    }

    public Integer bulkAdd(List<InventoryForm> inventoryFormList)throws ApiException
    {
        if(isEmpty(inventoryFormList))
        {
            throw new ApiException("Empty Inventory Form List");
        }
        validateInventoryList(inventoryFormList);
        List<InventoryPojo> inventoryPojoList=new ArrayList<>();
        for(InventoryForm inventoryForm : inventoryFormList)
        {
            InventoryPojo inventoryPojo=convertInventoryFormtoInventoryPojo(inventoryForm);
            validateBarcode(inventoryPojo);
            inventoryPojoList.add(inventoryPojo);
        }
        inventoryService.bulkAdd(inventoryPojoList);
        return inventoryPojoList.size();
    }

    public InventoryDataUI update(InventoryForm inventoryForm)throws ApiException
    {
        validateInventoryForm(inventoryForm);
        inventoryForm=normalize(inventoryForm);
        InventoryPojo inventoryPojo=convertInventoryFormtoInventoryPojo(inventoryForm);
        validateBarcode(inventoryPojo);
        inventoryService.update(inventoryForm);
        return convertInventoryFormtoInventoryDataUI(inventoryForm);
    }

    private void validateInventoryList(List<InventoryForm> inventoryFormList)throws ApiException
    {
        List<String> errorList = new ArrayList<>();
        Set<String> barcodeSet = new HashSet<>();
        Integer row = 1;
        for(InventoryForm inventoryForm : inventoryFormList)
        {
            if(!checkNotNullBulkUtil(inventoryForm))
            {
                errorList.add("Error : row = "+ row +  "barcode or quantity cant be Null");
                continue;
            }
            ProductPojo productPojo = productService.selectByBarcode(inventoryForm.getBarcode());

            if(isNull(productPojo))
            {
                errorList.add("Error : row = "+ row + " product with barcode "+ inventoryForm.getBarcode() + "does not exist");
            }
            if(barcodeSet.contains(inventoryForm.getBarcode()))
            {
                errorList.add("Error : row = "+ row + " barcode should not be repeated , barcode : "+inventoryForm.getBarcode());
            }
            else
            {
                barcodeSet.add(inventoryForm.getBarcode());
            }
        }
        if(!isEmpty(errorList))
            throwError(errorList);
    }

    private void validateBarcode(InventoryPojo inventoryPojo)throws ApiException
    {
        if(isNull(productService.selectByBarcode(inventoryPojo.getBarcode())))
        {
            throw new ApiException("Product with given barcode does not exist");
        }
    }

    public void validateInventoryForm(InventoryForm inventoryForm)throws ApiException
    {
        if(isNull(inventoryForm.getBarcode()) || inventoryForm.getBarcode().isEmpty())
        {
            throw new ApiException("Barcode cannot be Empty!");
        }
        if(isNull(inventoryForm.getQuantity()) || inventoryForm.getQuantity()<0)
        {
            throw new ApiException("Quantity cannot be Empty or Negative!");
        }
    }

    public InventoryForm normalize(InventoryForm inventoryForm){
        inventoryForm.setBarcode(inventoryForm.getBarcode().trim());
        inventoryForm.setQuantity(inventoryForm.getQuantity());
        return inventoryForm;
    }

    public List<InventoryReport> getInventoryReport()throws ApiException
    {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        if(isEmpty(inventoryPojoList))
        {
            throw new ApiException("No Inventory");
        }

        List<InventoryReport> inventoryReportList = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList)
        {
            InventoryReport inventoryReport = new InventoryReport();
            ProductPojo productPojo = productService.selectByBarcode(inventoryPojo.getBarcode());
            BrandPojo brandPojo = brandService.get(productPojo.getBrandCategoryId());

            inventoryReport.setBrand(brandPojo.getBrand());
            inventoryReport.setCategory(brandPojo.getCategory());
            inventoryReport.setQuantity(inventoryPojo.getQuantity());

            inventoryReportList.add(inventoryReport);
        }
        return inventoryReportList;
    }

}
