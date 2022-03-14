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
