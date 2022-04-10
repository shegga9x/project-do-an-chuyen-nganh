import { Component, OnInit, OnDestroy } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { CourseManageService } from 'src/app/services/course-manage.service';

@Component({
  selector: 'app-academic-events',
  templateUrl: './academic-events.component.html',
  styleUrls: ['./academic-events.component.scss'],
})
export class AcademicEventsComponent implements OnInit, OnDestroy {
  listSubAvailable: any[] = [];
  listCourseRegisterFake: any[] = [];

  // listCourseRegistRequests: Map<string, boolean> = new Map<string, boolean>();
  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService
  ) {
    this.titleService.setTitle('Academic Events');
  }

  ngOnInit(): void {
    this.getSubAvailable();
    this.getListCourseRegisterFake();
    //this.getSemesterReuslt();
  }

  // change listSubAvailable in courseManagerServices to empty when redirect to other page
  ngOnDestroy(): void {
    this.courseManageService.listSubAvailable = [];
  }

  updateListAndSaveToDB(idCourseOffering: string, checked: HTMLInputElement): void {
    this.courseManageService.submitCourseRegisterFake(idCourseOffering).subscribe({
      next: () => {
        this.courseManageService.getCourseRegisterFake().subscribe({
          next: (x: any) => {
            this.listCourseRegisterFake = x;
          }
        })
      },
      error: (error) => {
        checked.checked = false;
        alert(error);
      }
    });
  }

  selectAllDeleteBox(check: HTMLInputElement) {
    if (check.checked == true) {
      let deletebox: any[] = Array.from(document.getElementsByClassName('deletebox'));
      deletebox.forEach(e => e.checked = true);
    } else {
      let deletebox: any[] = Array.from(document.getElementsByClassName('deletebox'));
      deletebox.forEach(e => e.checked = false);
    }
  }

  submit() {
    this.courseManageService.submitCourseRegist().subscribe({
      next: () => {
        this.getSubAvailable();
        this.getListCourseRegisterFake();
      },
      error: (error) => {
        alert(error);
      }
    });
  }

  submitDelete() {
    let deletebox: any[] = Array.from(document.getElementsByClassName('deletebox'));
    let listIdCourse: string[] = [];
    deletebox.forEach(x => {
      if (x.checked == true) {
        listIdCourse.push(x.value)
      }
    });
    this.courseManageService.deleteCourseRegister(listIdCourse).subscribe({
      next: (x: any) => {
        this.getSubAvailable();
        this.getListCourseRegisterFake();
      },
      error: (error) => {
        alert(error);
      }
    })
  }

  getSubAvailable() {
    this.courseManageService.getSubAvailableRegist().subscribe({
      next: (x: any) => {
        this.listSubAvailable = x;
        //add to course-manage services
        this.courseManageService.listSubAvailable = this.listSubAvailable;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getListCourseRegisterFake() {
    this.courseManageService.getCourseRegisterFake().subscribe({
      next: (x: any) => {
        this.listCourseRegisterFake = x;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  findIdCourse(idCourse: string) {
    const even = (element: any) => {
      return element.idCourse == idCourse;
    };
    return this.listCourseRegisterFake.some(even);
  }

  filterSubAvaiable(idMonHoc: string) {
    if (idMonHoc == '') {
      this.listSubAvailable = this.courseManageService.listSubAvailable;
      return;
    }
    this.listSubAvailable = this.courseManageService.listSubAvailable.filter(
      (x) => x['courseDTO'].idCourse == idMonHoc
    );
  }
}
