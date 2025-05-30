import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { response } from 'express';
import { Url } from 'url';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http:HttpClient) {}

  getShortUrl(longUrl: String) {
    const payload = { longURL: longUrl };
    const apiUrl = 'http://localhost:8080/api/short_it';

    return this.http.post<any>(apiUrl, payload);
  }
}
