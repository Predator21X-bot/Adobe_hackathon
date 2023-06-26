from adobe.pdfservices.extractpdf import ExtractPdf
from adobe.pdfservices.pdfservicesclient import PdfServicesClient
from adobe.pdfservices.operation.auth.credentials import Credentials
import csv

# Adobe PDF Services credentials
CLIENT_ID = 'e2c3a983a8d345129e5feae80b286380'
CLIENT_SECRET = 'p8e-z6zJBte-MqzH08ft5x86UksD9Ul2k9il'

# Function to extract data from a PDF invoice
def extract_invoice_data(pdf_path):
    # Set up credentials
    credentials = Credentials.service_account_credentials_builder() \
        .with_client_id(CLIENT_ID) \
        .with_client_secret(CLIENT_SECRET) \
        .build()

    # Create an instance of the client
    client = PdfServicesClient(credentials)

    # Create an instance of the extract operation
    extract_operation = ExtractPdf \
        .builder() \
        .with_source_path(pdf_path) \
        .build()

    # Execute the extract operation
    result = client.execute_operation(extract_operation)

    # Get the extracted table data
    extracted_data = result.tables[0].rows

    return extracted_data

# Example usage
def main():
    # Path to the PDF invoice
    pdf_path = 'path/to/invoice.pdf'

    # Extract data from the PDF invoice
    invoice_data = extract_invoice_data(pdf_path)

    # Save the extracted data to a CSV file
    csv_path = 'path/to/extracted_data.csv'
    with open(csv_path, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerows(invoice_data)

    print('Data extracted and saved successfully.')

if __name__ == '__main__':
    main()
