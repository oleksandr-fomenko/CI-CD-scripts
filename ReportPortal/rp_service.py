import requests
import json

class RpService():

    def __init__(self, rp_domen, rp_project, rp_mode, rp_token):
        self.rp_domen = rp_domen 
        self.rp_project = rp_project
        self.rp_mode = rp_mode
        self.url = f"{rp_domen}/api/v1/{rp_project}" 
        self.headers = {
            "content-type": "application/json",
            "Authorization": f"Bearer {rp_token}"
        }

    def get_ui_rp_link(self, launchId: str):
        rp_mode_add = ""
        if self.rp_mode == "Debug":
            rp_mode_add = "/userdebug"
        return f"{self.rp_domen}/ui/#{self.rp_project}{rp_mode_add}/all/{launchId}" 

    def get_rp_results_by_id(self, launchId: str):
        response = requests.get(url=f"{self.url}/launch/uuid/{launchId}", headers=self.headers)
        return json.loads(response.text)

    def get_rp_launches_by(self, rp_launch_name: str, rp_page_number: int, rp_size: int):
        response = requests.get(url=f"{self.url}/launch?filter.cnt.name={rp_launch_name}&page.sort=startTime,number,ASC&page.page={rp_page_number}&page.size={rp_size}", headers=self.headers)
        return json.loads(response.text)

    def get_rp_launches_content_by(self, rp_launch_name: str):
        results = []
        rp_page_number = 1
        rp_size = 100
        total_pages = 1
        body = self.get_rp_launches_by(rp_launch_name, rp_page_number, rp_size)

        rp_size = body["page"]["size"]
        total_pages = body["page"]["totalPages"]

        results.extend(body["content"])

        for currrectrp_page_number in range(2, total_pages + 1):
            body = self.get_rp_launches_by(rp_launch_name, currrectrp_page_number, rp_size)
            results.extend(body["content"])
        return results