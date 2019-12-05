import requests
import sys
import argparse
import json
import datetime
import os

base_url = "https://horaslog.herokuapp.com/"

def create_user(args):
    user = get_logged_user()
    if not user:
        print("É preciso estar logado como administrador para registrar um novo usuário.")
    else:
        data = {}
        data["username"] = args.username
        data["password"] = args.password
        nome = ""
        for n in args.nome:
            nome += n + " "
        data["name"] = nome

        url = '{0}users'.format(base_url)
        headers = {'Content-Type': 'application/json', 'Authorization':user["accessToken"]}

        print("conectando ao servidor")
        response = requests.post(url, headers=headers, json=data)

        if response.status_code == 200:
            message = json.loads(response.content.decode('utf-8'))["message"]
            print(message)
            
        elif response.status_code == 400:
            print("Error: " + json.loads(response.content.decode('utf-8'))["errors"][0]['defaultMessage'])
        elif response.status_code == 401:
            print(json.loads(response.content.decode('utf-8'))["message"])
        elif response.status_code == 403:
            print("Erro: é preciso estar logado como administrador para registrar um novo usuário. ")
        else:
            print(json.loads(response.content.decode('utf-8')))

def users(args):
    print("conectando ao servidor")
    response = requests.get('{0}users'.format(base_url))

    if response.status_code == 200:
        response = json.loads(response.content.decode('utf-8'))

    for user in response:
        print ("Nome:{0} \nUsername:{1}\n".format(user['name'], user['username']))

def log(args):
    user = get_logged_user()
    if not user:
        print("Para poder criar um registro você precisa estar logado.")
        return
    
    data = {}
    data["userId"] = str(get_id_by_username(user["username"]))
    data["date"] = datetime.datetime.now().strftime("%Y-%m-%d")
    data["workedHours"] = args.horas
    

    url = '{0}records'.format(base_url)
    headers = {'Content-Type': 'application/json', 'Authorization':user["accessToken"]}

    print("conectando ao servidor")
    response = requests.post(url, headers=headers, json=data)

    if response.status_code == 200:
        message = json.loads(response.content.decode('utf-8'))["message"]
        print(message)
        
    elif response.status_code == 400:
       # print(json.loads(response.content.decode('utf-8'))["errors"][0]['defaultMessage'])
       print(response.content.decode('utf-8'))
    elif response.status_code == 401:
        print(json.loads(response.content.decode('utf-8'))["message"])


def login(args):
    url = '{0}login'.format(base_url)
    headers = {'Content-Type': 'application/json'}
    data = {"username":args.username, "password":args.password}

    response = requests.post(url, headers=headers, json=data)

    if response.status_code == 200:
        #token = json.loads(response.content.decode('utf-8'))["accessToken"]
        
        user_data = {"username":args.username, "password":args.password}

        f = open("credentials.txt", "w")
        f.write(json.dumps(user_data))
        f.close()

        print("Login realizado.")
    elif response.status_code == 400:
        print("ERRO: " + json.loads(response.content.decode('utf-8'))["errors"][0]['defaultMessage'])
    elif response.status_code == 401:
        print("ERRO: " + json.loads(response.content.decode('utf-8'))["message"])

def logout(args):
    if get_logged_user():
        os.remove("credentials.txt")
        print("logout efetuado.")
    else:
        print("Não há um usuário logado.")

def records(args):
    if args.a == True:
        print("conectando ao servidor")
        response = requests.get('{0}users'.format(base_url))

        if response.status_code == 200:
            response = json.loads(response.content.decode('utf-8'))

        for user in response:
            sum = 0
            for record in user['hoursRecords']:
                sum += float(record['workedHours'])

            print ("Nome:{0} \nHoras:{1}\n".format(user['name'], sum))

    elif args.n:
        try:
            id = get_id_by_username(args.n)
        except ValueError as ve:
            print("Usuário não existe ou username incorreto.")
            return
        print("conectando ao servidor")
        response = requests.get('{0}records/user/{1}'.format(base_url, id))

        if response.status_code == 200:
            response = json.loads(response.content.decode('utf-8'))

        sum = 0
        output = ""
        for record in response:
            sum += float(record['workedHours'])
            
            data = parse_date(record['date'])

            output += "\nData: " + data.strftime("%m/%d/%Y") + "\nHoras: " + str(record['workedHours'])
            

        print ("Registros do Usuário {0}.\nTotal de horas trabalhadas: {1}.\n{2}".format(args.n, sum, output))

    else:
        user = get_logged_user()
        if user:
            print("conectando ao servidor")
            response = requests.get('{0}records/user/{1}'.format(base_url, get_id_by_username(user["username"])))

            if response.status_code == 200:
                response = json.loads(response.content.decode('utf-8'))

            sum = 0
            output = ""
            for record in response:
                sum += float(record['workedHours'])
                
                data = parse_date(record['date'])

                output += "\n\nData: " + data.strftime("%m/%d/%Y") + "\nHoras: " + str(record['workedHours'])
                

            print ("Registros do Usuário {0}.\nTotal de horas trabalhadas: {1}.\n{2}".format(user["username"], sum, output))
        else:
            print("conectando ao servidor")
            response = requests.get('{0}users'.format(base_url))

            if response.status_code == 200:
                response = json.loads(response.content.decode('utf-8'))

            for user in response:
                sum = 0
                for record in user['hoursRecords']:
                    sum += float(record['workedHours'])

                print ("Nome:{0} \nHoras:{1}".format(user['name'], sum))
    

def main(argv):
    parser = argparse.ArgumentParser(prog='horaslog')
    subparsers = parser.add_subparsers()

    # cria o parser para o login
    parser_login = subparsers.add_parser('login', help='Realiza o login com o usuário informado.')
    parser_login.add_argument('username', type=str, help='Nome do usuário')
    parser_login.add_argument('password', type=str, help='Senha do usuário')
    parser_login.set_defaults(func=login)

    # cria o parser para o logout
    parser_login = subparsers.add_parser('logout', help='Realiza o logout do usuário logado.')
    parser_login.set_defaults(func=logout)

    # cria o parser para ver o registro de horas
    parser_records = subparsers.add_parser('records', help='Mostra os registro do usuário logado, de um específico ou de todos.')
    parser_records.add_argument("-n", required=False, type=str, help="Username do usuário desejado.")
    parser_records.add_argument("-a", required=False, action="store_true", help="Coloque se deseja ver as horas de todos os usuários.")
    parser_records.set_defaults(func=records)

    # cria o parser para mostrar todos os usuários
    parser_users = subparsers.add_parser('users', help="Mostra todos os usuários.")
    parser_users.set_defaults(func=users)

    # cria o parser para criar um registro
    parser_log = subparsers.add_parser('log', help="Cria um novo registro de horas.")
    parser_log.add_argument("horas", type=float, help="Número de horas trabalhadas.")
    parser_log.set_defaults(func=log)

    # cria o parser para criar um usuário
    parser_create_user = subparsers.add_parser('nuser', help="Cria um novo usuário. (Precisa ser admin).")
    parser_create_user.add_argument('username', type=str, help='Nome de usuário do novo usuário')
    parser_create_user.add_argument('password', type=str, help='Senha do novo usuário')
    parser_create_user.add_argument('nome', type=str, help='Nome real do novo usuário', nargs='+')
    parser_create_user.set_defaults(func=create_user)

    args = parser.parse_args()
    args.func(args)

def parse_date(date):
    data = str(date).split(sep='T')
    
    return datetime.datetime.strptime(data[0], '%Y-%m-%d')

def get_id_by_username(username):
    response = requests.get('{0}users'.format(base_url))

    if response.status_code == 200:
        response = json.loads(response.content.decode('utf-8'))

    for user in response:
        if str(user['username']).__eq__(username):
            return user['id']

    raise ValueError

def get_logged_user():
    try:
        f = open("credentials.txt")
        text = f.read()
        j = json.loads(text)
        f.close()

        url = '{0}login'.format(base_url)
        headers = {'Content-Type': 'application/json'}
        data = {"username":j["username"], "password":j["password"]}

        response = requests.post(url, headers=headers, json=data)

        if response.status_code == 200:
            token = json.loads(response.content.decode('utf-8'))["accessToken"]
            j["accessToken"] = token
        else:
            os.remove("credentials.txt")
            return None

    except FileNotFoundError as nf:
        return None        

    return j

if __name__ == "__main__":
   main(sys.argv[1:])