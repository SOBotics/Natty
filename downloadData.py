import requests

url = "https://logs.sobotics.org/napi/api/list/"


endpoints = ["data/blacklistedWords", "data/whitelistedWords"]
files = ["BlackListedWords.txt", "WhiteListedWords.txt"]

for api, filename in zip(endpoints, files):
    resp = requests.get(url=url+api)
    data = resp.json()
    items = [i["name"] for i in data["items"]]
    print ("\n".join(items), file=open(filename, "w"))