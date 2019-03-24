from flask import Flask, render_template, flash, redirect, url_for, session, request, logging, json

app = Flask(__name__)
class jresp:
    def __init__(self, token):
        self.token = token
    def serialize(self):
        return {
            'token': self.token,
        }
    
 
@app.route('/',methods=['GET', 'POST'])
def index():
    print(request.data)
    data = (json.loads(request.data))
    print(data)
    response = 0
    if data['username'] == 'user' and data['password'] == 'pass' :
        data = {"token":"adasda","url":"http://192.168.1.120/oneauth"}
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

@app.route('/oneauth',methods=['GET', 'POST'])
def onefactor():
    data = (json.loads(request.data))
    if data['username'] == 'user' and data['token'] == "adasda" :
        data = {"token":"adasda","url":"http://192.168.1.120/twoauth"}
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



@app.route('/about')
def about():
    return render_template('about.html')

@app.route('/twoauth')
def twoauth():
    return render_template('twoauth.html')




if __name__ == '__main__':
	app.secret_key="ServeMeOutsideHowBoutFlask"
app.run(host='0.0.0.0',port=80)
