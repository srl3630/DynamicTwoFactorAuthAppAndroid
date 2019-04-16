package com.example.twoauth;


public class Creds {
    private int _id;
    private String _url;
    private String _user;
    private String _token;
    private int _counter;


    public Creds(int _id, String _url, String _user, String _token, int _counter) {
        this._id = _id;
        this._url = _url;
        this._user = _user;
        this._token = _token;
        this._counter = _counter;
    }

    public int get_id() {
        return _id;
    }

    public String get_url() {
        return _url;
    }

    public String get_user() {
        return _user;
    }

    public String get_token() {
        return _token;
    }

    public int get_counter() {
        return _counter;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public void set_token(String _token) {
        this._token = _token;
    }

    public void set_counter(int _counter) { this._counter = _counter; }


}