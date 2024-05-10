#Database Connexion
import os

try:
    import libsql_experimental as libsql
except ImportError:
    os.system('pip install libsql_experimental')
    import libsql_experimental as libsql

url = 'https://renovlunette-project-ereps.turso.io'
token = 'eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTUzNzgwOTEsImlkIjoiZGRjZTJkOGQtY2VmOC00MTdmLThhY2YtNmJhMWNjY2I1ZjhiIn0.o_qmZwR-OhPoK4Zp6XRMdG9Ueeiw_axrNEmwJ2fWD5XNeVfr8S5_J5acHsLQI0GuoiYnzYl39kNMDHZSgW57Dg'


def connexion(url,token):
    print(F"connecting to {url}")
    conn = libsql.connect(url,
                        auth_token=token)
    conn.execute("CREATE TABLE IF NOT EXISTS  items (id INT PRIMARY KEY,image BLOB,description TEXT,qualitity INT, price REAL unsigned ,contact TEXT, rib TEXT);")
    conn.commit()
if __name__ == "__main__":
    connexion(url,token)