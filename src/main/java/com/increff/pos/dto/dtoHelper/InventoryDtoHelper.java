package com.increff.pos.dto.dtoHelper;


import com.increff.pos.model.DataUI.InventoryDataUI;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;

public class InventoryDtoHelper {

    public static InventoryData convertInventoryPojotoInventoryData(InventoryPojo inventoryPojo)
    {
        InventoryData inventoryData=new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setBarcode(inventoryPojo.getBarcode());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        return inventoryData;
    }

    public static InventoryPojo convertInventoryFormtoInventoryPojo(InventoryForm inventoryForm)
    {
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setBarcode(inventoryForm.getBarcode());
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }

    public static InventoryDataUI convertInventoryFormtoInventoryDataUI(InventoryForm inventoryForm)
    {
        InventoryDataUI inventoryDataUI=new InventoryDataUI();
        inventoryDataUI.setBarcode(inventoryForm.getBarcode());
        inventoryDataUI.setQuantity(inventoryForm.getQuantity());
        return inventoryDataUI;
    }
}
