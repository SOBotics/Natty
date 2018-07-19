import configparser

config = configparser.ConfigParser()

params = ['apikey', 'autoflagkey', 'autoflagtoken', 'userid', 'username', 'location', 'email', 'password',
          'sentinelKey', 'sentinelApiKey', 'fmsPath', 'fmsUrl', 'feedMsg']

for param in params:
    in_data = input("Enter the value for the parameter - {} (Press Enter to leave it empty)".format(param))
    config[param] = in_data

with open('../properties/login.properties', 'w') as configfile:
    config.write(configfile)

print("Generated Config Files")