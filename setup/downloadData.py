import requests

url = "https://logs.sobotics.org/napi/api/list/"


endpoints = ["blacklistedWords", "whitelistedWords", "salutations"]
files = ["../data/BlackListedWords.txt", "../data/WhiteListedWords.txt", "../data/Salutations.txt"]

for api, filename in zip(endpoints, files):
    resp = requests.get(url=url+api)
    data = resp.json()
    items = [i["name"] for i in data["items"]]
    print ("\n".join(items), file=open(filename, "w"))