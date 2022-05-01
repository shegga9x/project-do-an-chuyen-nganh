declare var $: any;

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent implements OnInit {

  constructor() {
    $(document).ready(function () {
      // Toggle the side navigation
      $("#sidebarToggle, #sidebarToggleTop").on('click', function (e: any) {
        console.log('???????')
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
