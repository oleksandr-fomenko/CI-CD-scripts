import requests

class TeamsService():

    def __init__(self, url):
        self.url = url 
        self.headers = {
            "content-type": "application/json",
        }

    def post_comment(self, text: str):
        data = {
            "text": text
        }
        response = requests.post(url=self.url, json=data, headers=self.headers)
        return response