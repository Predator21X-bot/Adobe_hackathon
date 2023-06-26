import requests
import json

# Adobe PDF Services credentials
api_key = '6b15ec8b9619450698eef2295607a789'
base_url = 'https://pdfservices.adobe.com'

# curl -X POST 'https://ims-na1.adobelogin.com/ims/token/v3' -H 'Content-Type: application/x-www-form-urlencoded' -d 'grant_type=client_credentials&client_id=6b15ec8b9619450698eef2295607a789&client_secret=p8e-K6D70g74dowjhoIxN4DJh09APV6rVxND&scope=openid,AdobeID,DCAPI'

# Function to extract data from a PDF invoice
def extract_invoice_data(pdf_url):
    # API endpoint
    endpoint = '/pdfservices/v1/documents'

    # Request headers
    headers = {
        'Content-Type': 'application/json',
        'x-api-key': api_key
    }

    # Request payload
    payload = {
        'url': pdf_url,
        'options': {
            'extract:table': True,
            'extract:footer': False,
            'extract:header': False
        }
    }

    # Make the API request
    response = requests.post(base_url + endpoint, headers=headers, json=payload)

    if response.status_code == 200:
        result = json.loads(response.text)
        return result['tables'][0]['rows']
    else:
        print(f"Error extracting data: {response.text}")

# Example usage
pdf_invoice_url = 'https://example.com/invoice.pdf'
invoice_data = extract_invoice_data(pdf_invoice_url)

# Print extracted data
for row in invoice_data:
    print(row)
