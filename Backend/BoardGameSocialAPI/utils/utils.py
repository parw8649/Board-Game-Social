import json


def read_json(file_name):
    with open(file_name) as file:
        return json.load(file)
