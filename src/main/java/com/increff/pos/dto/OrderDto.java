package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;

import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import javafx.util.Pair;
import org.apache.fop.apps.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.increff.pos.dto.dtoHelper.OrderDtoHelper.convertOrderPojotoOrderData;
import static com.increff.pos.dto.dtoHelper.OrderItemDtoHelper.convertOrderItemPojotoOrderItemData;
import static com.increff.pos.util.HelperUtil.jaxbObjectToXML;
import static com.increff.pos.util.HelperUtil.returnFileStream;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class OrderDto {
    private final FopFactory fopFactory= FopFactory.newInstance(new File(".").toURI());

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    BrandService brandService;
    @Autowired
    ProductService productService;


    public OrderData get(Integer id)throws ApiException{
        return convertOrderPojotoOrderData(orderService.get(id));
    }

    public List<OrderData> getAll()throws ApiException
    {
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for(OrderPojo orderPojo : orderPojoList)
        {
            orderDataList.add(convertOrderPojotoOrderData(orderPojo));
        }
        return orderDataList;
    }

    public OrderData add()throws ApiException
    {
        return convertOrderPojotoOrderData(orderService.add());
    }

    public Integer updateOrderStatusPlaced(Integer id)throws ApiException
    {
        if(isEmpty(getOrderItemforOrderUtil(id)))
        {
            throw new ApiException("Cannot Place Empty Order with Order Id :"+id);
        }
        orderService.updateOrderStatusPlaced(id);
        return id;
    }

    public void getOrderInvoice(int orderId, HttpServletResponse response)throws ApiException, IOException, TransformerException
    {
        List<OrderItemPojo> orderItemPojoList = getOrderItemforOrderUtil(orderId);
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList)
        {
            OrderItemData orderItemData = convertOrderItemPojotoOrderItemData(orderItemPojo);
            orderItemDataList.add(orderItemData);
        }

        ZonedDateTime time = orderService.get(orderId).getTime();
        Double total=0.0;

        for(OrderItemData orderItemData : orderItemDataList)
        {
            total+=orderItemData.getQuantity()*orderItemData.getSellingPrice();
        }
        InvoiceData invoiceData2 =new InvoiceData(orderItemDataList,time,total,orderId);

        String xml = jaxbObjectToXML(invoiceData2);
        File xsltFile = new File("src", "invoice.xsl");
        System.out.println(xml);
        byte[] pdfbytes = convertToPDF(xsltFile, xml);
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + "Invoice.pdf");
        response.setContentLengthLong(pdfbytes.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(pdfbytes);
        baos.writeTo(response.getOutputStream());
        baos.close();
    }

    private byte[] convertToPDF(File xslt, String xml) throws IOException, TransformerException {

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // configure foUserAgent as desired

        // Setup output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            // Construct fop with desired output format
            Fop fop = null;
            try {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            } catch (FOPException e) {
                throw new RuntimeException(e);
            }

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));

            // Setup input for XSLT transformation
            Source src = new StreamSource(new StringReader(xml));
//                        Source src = new StreamSource(new FileInputStream(xml));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } catch (FOPException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
        try(OutputStream outputStream = new FileOutputStream("invoice.pdf")) {
            out.writeTo(outputStream);
        }
         return out.toByteArray();
    }

    public List<OrderItemPojo> getOrderItemforOrderUtil(Integer orderId)throws ApiException
    {
        OrderPojo orderPojo = orderService.get(orderId);
        if(isNull(orderPojo))
        {
            throw new ApiException("Order with this Id does not exist , id :"+orderId);
        }
        return orderItemService.selectFromOrderId(orderId);

    }

    @Transactional(rollbackFor = ApiException.class)
    public List<SalesReport> getSalesReport(SalesReportForm salesReportForm)throws ApiException
    {
        ZonedDateTime from = salesReportForm.getFrom();
        ZonedDateTime to =salesReportForm.getTo();

        if(ChronoUnit.DAYS.between(from,to)>30)
        {
            throw new ApiException("ZonedDateTime Range should not be greater than 30 days");
        }
        if(ChronoUnit.DAYS.between(from,to)<=0)
        {
            throw new ApiException("From ZonedDateTime should not be Greater than to ZonedDateTime");
        }

        if(salesReportForm.getBrand()!="" && salesReportForm.getCategory()!="" && brandService.selectByBrandCategory(salesReportForm.getBrand(), salesReportForm.getCategory())==null)
        {
            throw new ApiException("The Given Brand Category Combination does not exist");
        }
        if(brandService.selectByBrand(salesReportForm.getBrand())==null)
        {
            throw new ApiException("Brand does not exisr");
        }
        if(brandService.selectByCategory(salesReportForm.getCategory())==null)
        {
            throw new ApiException("Category does not exist");
        }

        List<SalesReport> salesReportsDataList = getSalesReportUtil(salesReportForm);
        if(isEmpty(salesReportsDataList))
        {
            throw new ApiException("No Sales for a givwn range");
        }
        return salesReportsDataList;

    }

    public List<SalesReport> getSalesReportUtil(SalesReportForm salesReportForm)throws ApiException
    {
        List<OrderPojo> orderPojoList = orderService.selectByFromDate(salesReportForm.getFrom(),salesReportForm.getTo());

        List<Integer> orderIdList = orderPojoList.stream().map(orderPojo -> orderPojo.getId()).collect(Collectors.toList());

        if(isEmpty(orderIdList))
        {
            throw new ApiException("No Sales");
        }

        List<OrderItemPojo> orderItemPojoList = orderItemService.selectFormOrderIdList(orderIdList);
        List<ProductPojo> productPojoList=new ArrayList<>();

        if(salesReportForm.getBrand()!="" && salesReportForm.getCategory()!="")
        {
            BrandPojo brandPojoList=brandService.selectByBrandCategory(salesReportForm.getBrand(), salesReportForm.getCategory());
            ProductPojo productPojo = productService.get(brandPojoList.getId());
            productPojoList.add(productPojo);
        }
        else if(salesReportForm.getBrand()=="" && salesReportForm.getCategory()!="")
        {
            List<BrandPojo> brandPojoList=brandService.selectByCategory(salesReportForm.getCategory());
            for(BrandPojo brandPojo : brandPojoList)
            {
                ProductPojo productPojo = productService.get(brandPojo.getId());
                productPojoList.add(productPojo);
            }
        }
        else if(salesReportForm.getBrand()!="" && salesReportForm.getCategory()=="")
        {
            List<BrandPojo> brandPojoList=brandService.selectByBrand(salesReportForm.getBrand());
            for(BrandPojo brandPojo : brandPojoList)
            {
                ProductPojo productPojo =productService.get(brandPojo.getId());
                productPojoList.add(productPojo);
            }
        }
        else
        {
            productPojoList=productService.getAll();
        }
        List<SalesReport> salesData = getSalesData(orderItemPojoList,productPojoList);
        List<SalesReport> salesReportData = generatePerfectSalesReportData(salesData);
        return salesReportData;

    }

    private List<SalesReport> getSalesData(List<OrderItemPojo> orderItemPojoList, List<ProductPojo> productPojoList)throws ApiException
    {
        //For generating All Sales Data
        List<SalesReport> commonPojo=new ArrayList<>();
        Map<Integer,ProductPojo> productPojoMap=new HashMap<>();

        //Hashmap mapping Product ids with its Product Pojos
        for(ProductPojo productPojo : productPojoList)
        {
            productPojoMap.put(productPojo.getId(), productPojo);
        }

        for(OrderItemPojo orderItemPojo : orderItemPojoList)
        {
            if(productPojoMap.containsKey(orderItemPojo.getId()))
            {
                ProductPojo temp=productPojoMap.get(productService.selectByBarcode(orderItemPojo.getBarcode()).getId());
                BrandPojo brandPojo = brandService.get(temp.getBrandCategoryId());
                SalesReport salesReport = new SalesReport(brandPojo.getBrand(), brandPojo.getCategory(),  orderItemPojo.getQuantity(), orderItemPojo.getSellingPrice() * orderItemPojo.getQuantity());
                commonPojo.add(salesReport);
            }
        }
        return commonPojo;
    }

    private List<SalesReport> generatePerfectSalesReportData(List<SalesReport> salesReportData)
    {
        //For generating Net Revenue of a Particular Brand-Category Pair
        Map<Pair<String, String>, SalesReport> map = new HashMap<>();
        for (SalesReport data : salesReportData)
        {
            Pair brandPair = new Pair(data.getBrand(), data.getCategory());
            if (map.containsKey(brandPair))
            {
                SalesReport salesReport = map.get(brandPair);
                int qty = salesReport.getQuantity() + data.getQuantity();
                double revenue = salesReport.getRevenue() + data.getRevenue();
                salesReport.setQuantity(qty);
                salesReport.setRevenue(revenue);
                map.put(brandPair, salesReport);
            }
            else
            {
                map.put(brandPair, data);
            }
        }
        List<SalesReport> salesReportList = new ArrayList<>(map.values());
        return salesReportList;
    }



}