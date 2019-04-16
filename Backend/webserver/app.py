from flask import Flask, render_template, flash, redirect, url_for, session, request, logging, json
import hashlib

app = Flask(__name__)
class jresp:
    def __init__(self, token):
        self.token = token
    def serialize(self):
        return {
            'token': self.token,
        }
    
 
@app.route('/auth',methods=['GET', 'POST'])
def index():
    print(request.data)
    data = (json.loads(request.data))
    print(data)
    response = 0
    if data['username'] == 'user' and data['password'] == 'pass' :
        data = {"token":"adasda"}
        response = app.response_class(
            response=json.dumps(data),
            status=200,
            mimetype='application/json'
        )
    else:
        data = {'error':'bad username or password'}
        response = app.response_class(
            response=json.dumps(data),
            status=401,
            mimetype='application/json'
        )
    return response

@app.route('/login',methods=['GET', 'POST'])
def onefactor():
    data = (json.loads(request.data))
    m = hashlib.sha256()
    m.update('adasda0').hexdigest()
    print(data['oauth'])
    print(m)

    if data['username'] == 'user' and data['oauth'] == m :
            data = {"oauth_token":"adasda"}
            response = app.response_class(
            response=json.dumps(data),
            status=200,
            mimetype='application/json'
        )
    else:
        data = {'error':'bad username or password'}
        response = app.response_class(
            response=json.dumps(data),
            status=401,
            mimetype='application/json'
        )
    return response



@app.route('/')
def about():
    return render_template('about.html')

@app.route('/twoauth')
def twoauth():
    return render_template('twoauth.html')




if __name__ == '__main__':
	app.secret_key="ServeMeOutsideHowBoutFlask"
app.run(host='0.0.0.0',port=8000)
