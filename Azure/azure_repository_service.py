import requests

class AzureRepositoryService():

    def __init__(self, organization_uri, project_id, repository_id, token):
        self.api_version = "6.0"
        self.organization_uri = organization_uri
        self.url = f"{organization_uri}{project_id}" \
                   f"/_apis/git/repositories/{repository_id}"
          
        self.headers = {
            "content-type": "application/json",
            "Authorization": f"Basic {token}"
        }