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

    def post_comment_to_pr(self, comment: str, pr_id: str, status=4):
        data = {
            "comments": [
                {
                    "parentCommentId": 0,
                    "content": comment,
                    "commentType": 1
                }
            ],
            "status": status
        }
        response = requests.post(url=f"{self.url}/pullRequests/{pr_id}/threads?api-version={self.api_version}", json=data, headers=self.headers)
        return response
