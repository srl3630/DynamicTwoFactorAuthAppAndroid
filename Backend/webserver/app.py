from flask import Flask, render_template, flash, redirect, url_for, session, request, logging, json
import hmac, hashlib
import string
import random

app = Flask(__name__)

passwords = {
    "alice": "pass",
    "bob": "word"
}

counters = {

}

tokens = {


}


@app.route('/login', methods=['POST'])
def login():
    """
    Handles the login process
    """
    if request.method == "POST":
        username = request.form["username"]
        otp = request.form["otp"]

        if int(otp) == generate_otp(username):  # validate otp
            counters[username][0] += 1  # increment counter
            # generate and store token
            token = ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(16))
            tokens[username] = token
            data = {"oauth_token": token}
        else:
            data = {'error': 'bad username or token'}
        return str(data)


@app.route('/register', methods=['POST'])
def register():
    """
    handles the register process
    """
    if request.method == "POST":
        username = request.form["username"]
        password = request.form["password"]

        if username in passwords.keys() and passwords[username] == password:  # validate passwords
            salt = random.randint(0, 256)  # generate salt
            data = {"salt": salt}
            counters[username] = [0, salt]  # save salt and initialize counter
            print(generate_otp(username))  # for debugging purposes
        else:
            data = {'error': 'bad username or password'}
        return str(data)


def generate_otp(username):
    """
    Generates an otp from the tables given a username
    """
    m = hmac.new(bytes([counters[username][1]]), digestmod=hashlib.sha256)
    m.update(bytes([counters[username][0]]))
    return int(m.hexdigest(), 16) % 1000000  # truncate it for debugging purposes


@app.route('/')
def about():
    return render_template('about.html')


app.run(host='localhost',port=8080)
