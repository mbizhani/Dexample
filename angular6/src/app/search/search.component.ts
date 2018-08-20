import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {map, flatMap, toArray} from 'rxjs/operators'

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchResult: string[];
  search: string;


  // ------------------------------

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
  }

  // ------------------------------

  fetchSearchResult() {
    //this.searchResult = ['A', 'B', 'C'];
    this.http.get<any[]>('http://localhost:8080/api/customer/byName/' + this.search).pipe(
      flatMap(value => value),
      // map(value => value.name),
      toArray()
    ).subscribe(value => this.searchResult = value);
  }

}
