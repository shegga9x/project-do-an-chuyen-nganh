import { GeneralService, PDTService } from 'src/app/services';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-close-course-regist',
  templateUrl: './close-course-regist.component.html',
  styleUrls: ['./close-course-regist.component.scss']
})
export class CloseCourseRegistComponent implements OnInit {
  listSubAvailable: any[] = [];
  listRequest: string[] = [];
  constructor(private titleService: Title,
    private pDTService: PDTService,
    private generalService: GeneralService) { }

  ngOnInit(): void {
    this.getSubAvailable();
  }
  getSubAvailable() {
    this.pDTService.getCloseCourseRegist().subscribe({
      next: (x: any) => {
        console.log(x);
        this.listSubAvailable = x;
        //add to course-manage services
        this.pDTService.listSubAvailable = this.listSubAvailable;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
  findIdCourse(idCourse: string) {
    const even = (element: any) => {
      return element.idCourse == idCourse;
    };
  }

  filterSubAvaiable(idMonHoc: string) {
    if (idMonHoc == '') {
      this.listSubAvailable = this.pDTService.listSubAvailable;
      return;
    }
    this.listSubAvailable = this.pDTService.listSubAvailable.filter(
      (x) => x['courseDTO'].idCourse == idMonHoc
    );
  }
  selectAllDeleteBox(check: HTMLInputElement) {
    if (check.checked == true) {
      let deletebox: any[] = Array.from(
        document.getElementsByClassName('deletebox')
      );
      deletebox.forEach((e) => (e.checked = true));
    } else {
      let deletebox: any[] = Array.from(
        document.getElementsByClassName('deletebox')
      );
      deletebox.forEach((e) => (e.checked = false));
    }
  }
  listCheckAdd(id: any) {
    const index = this.listRequest.indexOf(id, 0);
    if (index > -1) {
      this.listRequest.splice(index, 1);
    } else {
      this.listRequest.push(id)
    }
  }
  

  deleteCourseOffering() { this.pDTService.deleteCourseOffering(this.listRequest).subscribe() }
}
