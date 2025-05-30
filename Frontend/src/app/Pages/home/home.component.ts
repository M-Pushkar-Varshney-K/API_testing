import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductsService } from '../../services/products.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  longUrl: String = '';
  shortUrl: String = '';

  constructor(private productService: ProductsService){}  

  shortUrlFun(){
    // this.shortUrl = this.longUrl;
    // console.log(this.longUrl);
    this.productService.getShortUrl(this.longUrl).subscribe({
      next: (response: any) => {
        this.shortUrl = response.shortURL;
        console.log('Shortened URL:', this.shortUrl);
      },
      error: err => {
        console.error('Error:', err);
      }
    });
  }

  copyToClipboard() {
    if(navigator.clipboard && this.shortUrl) {
      navigator.clipboard.writeText(this.shortUrl.toString()).then(() => {
        alert("Copied to Clipboard");
      }).catch(err => {
        console.error('Failed to copy the url:', err);
        alert("Failed to copy the URL");
      })
    }
  }

  // DONOT KNOW WHY THIS IS NOT WORKING
  // ngOnInit() {
  //   this.productService.getShortUrl().subscribe((shortedUrl: any) =>{
  //     console.log('Shortened URL:', shortedUrl);
  //     this.shortUrl = shortedUrl.toString();
  //   })
  // }
}
