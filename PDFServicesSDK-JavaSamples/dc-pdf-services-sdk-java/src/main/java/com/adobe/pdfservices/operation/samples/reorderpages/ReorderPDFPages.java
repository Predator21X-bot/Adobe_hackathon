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

package com.adobe.pdfservices.operation.samples.reorderpages;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.exception.SdkException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.ReorderPagesOperation;
import com.adobe.pdfservices.operation.pdfops.options.PageRanges;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This sample illustrates how to reorder the pages in a PDF file.
 * <p>
 * Refer to README.md for instructions on how to run the samples.
 */
public class ReorderPDFPages {

    // Initialize the logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(ReorderPDFPages.class);

    public static void main(String[] args) {
        try {
            // Initial setup, create credentials instance.
            Credentials credentials = Credentials.servicePrincipalCredentialsBuilder()
                    .withClientId(System.getenv("PDF_SERVICES_CLIENT_ID"))
                    .withClientSecret(System.getenv("PDF_SERVICES_CLIENT_SECRET"))
                    .build();

            // Create an ExecutionContext using credentials and create a new operation instance.
            ExecutionContext executionContext = ExecutionContext.create(credentials);
            ReorderPagesOperation reorderPagesOperation = ReorderPagesOperation.createNew();

            // Set operation input from a source file, along with specifying the order of the pages for
            // rearranging the pages in a PDF file.
            FileRef source = FileRef.createFromLocalFile("src/main/resources/reorderPagesInput.pdf");
            PageRanges pageRanges = getPageRangeForReorder();
            reorderPagesOperation.setInput(source);
            reorderPagesOperation.setPagesOrder(pageRanges);

            // Execute the operation.
            FileRef result = reorderPagesOperation.execute(executionContext);

            // Save the result to the specified location.
            String outputFilePath = createOutputFilePath();
            result.saveAs(outputFilePath);

        } catch (IOException | ServiceApiException | SdkException | ServiceUsageException e) {
            LOGGER.error("Exception encountered while executing operation", e);
        }
    }

    private static PageRanges getPageRangeForReorder() {
        // Specify order of the pages for an output document.
        PageRanges pageRanges = new PageRanges();
        // Add pages 3 to 4.
        pageRanges.addRange(3, 4);

        // Add page 1.
        pageRanges.addSinglePage(1);

        return pageRanges;
    }

    //Generates a string containing a directory structure and file name for the output file.
    public static String createOutputFilePath(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = dateTimeFormatter.format(now);
        return("output/ReorderPDFPages/reorder" + timeStamp + ".pdf");
    }
}