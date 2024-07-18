import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [
    FormsModule
  ],
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  name: string ="";
  surname: string="";
  identity_number: string ="";
  birthDate: Date = new  Date();
  email: string ="";
  password: string ="";



  constructor(private http: HttpClient )
  {

  }
  save()
  {

    let bodyData = {
      "name" : this.name,
      "surname":this.surname,
      "email" : this.email,
      "password" : this.password,
      "identity_number":this.identity_number,
      "birth_date" : this.birthDate
    };
    this.http.post("http://localhost:8080/auth/signup",bodyData,{responseType: 'text'}).subscribe((resultData: any)=>
    {
      console.log(resultData);
      alert("User Registered Successfully");

    });
  }

}
