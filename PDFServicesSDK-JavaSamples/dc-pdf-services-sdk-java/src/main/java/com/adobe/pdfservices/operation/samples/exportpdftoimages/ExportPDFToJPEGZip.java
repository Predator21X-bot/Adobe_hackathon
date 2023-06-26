/*
 * Copyright 2019 Adobe
 * All Rights Reserved.
 *
 * NOTICE: Adobe permits you to use, modify, and distribute this file in
 * accordance with the terms of the Adobe license agreement accompanying
 * it. If you have received this file from a source other than Adobe,
 * then your use, modification, or distribution of it requires the prior
 * written permission of Adobe.
 */

package com.adobe.pdfservices.operation.samples.exportpdftoimages;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.exception.SdkException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.ExportPDFToImagesOperation;
import com.adobe.pdfservices.operation.pdfops.options.exportpdftoimages.ExportPDFToImagesTargetFormat;
import com.adobe.pdfservices.operation.pdfops.options.exportpdftoimages.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This sample illustrates how to export a PDF file to JPEG.
 * <p>
 * The resulting file is a ZIP archive containing one image per page of the source PDF file
 * <p>
 * Refer to README.md for instructions on how to run the samples.
 */
public class ExportPDFToJPEGZip {

    // Initialize the logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportPDFToJPEGZip.class);

    public static void main(String[] args) {
        try {

            // Initial setup, create credentials instance.
            Credentials credentials = Credentials.servicePrincipalCredentialsBuilder()
                    .withClientId(System.getenv("PDF_SERVICES_CLIENT_ID"))
                    .withClientSecret(System.getenv("PDF_SERVICES_CLIENT_SECRET"))
                    .build();

            //Create an ExecutionContext using credentials and create a new operation instance.
            ExecutionContext executionContext = ExecutionContext.create(credentials);
            ExportPDFToImagesOperation exportPDFToImagesOperation = ExportPDFToImagesOperation.createNew(ExportPDFToImagesTargetFormat.JPEG);

            // Set operation input from a source file.
            FileRef sourceFileRef = FileRef.createFromLocalFile("src/main/resources/exportPDFToImagesInput.pdf");
            exportPDFToImagesOperation.setInput(sourceFileRef);

            // Set the output type to create zip of images
            exportPDFToImagesOperation.setOutputType(OutputType.ZIP_OF_PAGE_IMAGES);

            // Execute the operation.
            List<FileRef> results = exportPDFToImagesOperation.execute(executionContext);

            LOGGER.info("Media type of the received asset is "+ results.get(0).getMediaType());

            // Save the result to the specified location.
            String outputFilePath = createOutputFilePath();
            results.get(0).saveAs(outputFilePath);

        } catch (ServiceApiException | IOException | SdkException | ServiceUsageException ex) {
            LOGGER.error("Exception encountered while executing operation", ex);
        }
    }

    //Generates a string containing a directory structure and file name for the output file.
    public static String createOutputFilePath(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = dateTimeFormatter.format(now);
        return("output/ExportPDFToJPEGZip/export" + timeStamp + ".zip");
    }
}
