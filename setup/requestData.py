import getpass

params = ['apikey', 'autoflagkey', 'autoflagtoken', 'userid', 'username', 'location', 'email', 'password',
          'sentinelKey', 'sentinelApiKey', 'fmsPath', 'fmsUrl', 'feedMsg']

sensitive = ['apikey', 'autoflagkey', 'autoflagtoken', 'password', 'sentinelKey', 'sentinelApiKey']

config_data = {}
print("Enter the value for the parameters (Press Enter to leave it empty).")
for param in params:
    if param in sensitive:
        in_data = getpass.getpass("{} (input hidden): ".format(param))
    else:
        in_data = input("{} : ".format(param))
    config_data[param] = in_data


with open('properties/login.properties', 'w') as config_file:
    for key, value in config_data.items():
        config_file.write("{}={}\n".format(key,value))

print("Generated Config Files")