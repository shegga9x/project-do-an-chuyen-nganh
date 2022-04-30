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
      // Toggle the side navigation
      $("#sidebarToggle, #sidebarToggleTop").on('click', function (e: any) {
        $("body").toggleClass("sidebar-toggled");
        $(".sidebar").toggleClass("toggled");
        if ($(".sidebar").hasClass("toggled")) {
          $('.sidebar .collapse').collapse('hide');
        };
      });
    });
  }

  ngOnInit(): void {

  }

}
