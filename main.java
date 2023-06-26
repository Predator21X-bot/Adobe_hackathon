import okhttp3.*;

import java.io.IOException;

public class InvoiceExtractor {
    // Adobe PDF Services credentials
    private static final String API_KEY = "6b15ec8b9619450698eef2295607a789";
    private static final String BASE_URL = "https://pdfservices.adobe.com";

    public static void main(String[] args) {
        // Example usage
        String pdfInvoiceUrl = "https://example.com/invoice.pdf";
        extractInvoiceData(pdfInvoiceUrl);
    }

    // Function to extract data from a PDF invoice
    private static void extractInvoiceData(String pdfUrl) {
        // API endpoint
        String endpoint = "/pdfservices/v1/documents";

        // Request headers
        Headers headers = new Headers.Builder()
                .add("Content-Type", "application/json")
                .add("x-api-key", API_KEY)
                .build();

        // Request payload
        String requestBody = "{\"url\":\"" + pdfUrl + "\",\"options\":{\"extract:table\":true,\"extract:footer\":false,\"extract:header\":false}}";

        // Create OkHttp client and request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .headers(headers)
                .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                .build();

        // Make the API request
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // Process the extracted data
                System.out.println(responseBody);
            } else {
                System.out.println("Error extracting data: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
