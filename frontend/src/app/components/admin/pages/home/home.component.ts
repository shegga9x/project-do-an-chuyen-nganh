declare var $: any;

import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor() {
    $(document).ready(function () {
      $('#dataTable').DataTable();
      $('#dataTable2').DataTable();
    });
  }

  ngOnInit(): void {

  }

}
