package com.increff.pos.util;

import com.increff.pos.model.InvoiceData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class HelperUtil {

    public static String jaxbObjectToXML(InvoiceData orderItemList) {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceData.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(orderItemList, sw);

            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static byte[] returnFileStream() throws FileNotFoundException {
        String path = "";
        File file = new File(path);
        if (file.exists()) {

            byte[] inFileBytes = new byte[0];
            try {
                byte[] bytes = loadFile(file);
                byte[] encoded = Base64.getEncoder().encode(bytes);
                byte[] pdf = Files.readAllBytes(file.toPath());
                return pdf;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


//            return new BufferedInputStream(new FileInputStream(file));
        }
        return null;
    }
    @SuppressWarnings("resource")
    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }
}
