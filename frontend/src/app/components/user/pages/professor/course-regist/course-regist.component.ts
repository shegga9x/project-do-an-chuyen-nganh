import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { CourseManageService, GeneralService } from 'src/app/services';

@Component({
  selector: 'app-course-regist',
  templateUrl: './course-regist.component.html',
  styleUrls: ['./course-regist.component.scss']
})
export class CourseRegistComponent implements OnInit {

  listSubAvailable: any[] = [];
  listCourseRegisterFake: any[] = [];

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
    private generalService: GeneralService
  ) {
    this.titleService.setTitle('Class Routine');
  }

  ngOnInit(): void {
    this.getListSubjectForProfressor();
    this.getListFake();
  }

  // change listSubAvailable in courseManagerServices to empty when redirect to other page
  ngOnDestroy(): void {
    this.courseManageService.listSubAvailable = [];
  }

  // lấy ds GV có thể dk
  getListSubjectForProfressor() {
    this.courseManageService.getListSubjectForProfressorRegist().subscribe({
      next: (x: any) => {
        this.listSubAvailable = x;
        //add to course-manage services
        this.courseManageService.listSubAvailable = this.listSubAvailable;
        // console.log(this.courseManageService.listSubAvailable);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  // lấy ds môn GV đã đk
  getListFake() {
    this.courseManageService.getCourseRegisterFakeForProfessor().subscribe({
      next: (x: any) => {
        this.listCourseRegisterFake = x;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  // checkbox và lưu danh sách xuống db
  checkCheckBoxvalue(
    idCourseOffering: string,
    checked: HTMLInputElement
  ): void {
    this.courseManageService
      .submitCourseForProfessor(idCourseOffering)
      .subscribe({
        next: () => {
          this.courseManageService
            .getListSubjectForProfressorRegist()
            .subscribe({
              next: (x: any) => {
                this.listCourseRegisterFake = x;
                this.getListSubjectForProfressor();
                this.getListFake();
              },
            });
        },
        error: (error) => {
          checked.checked = false;
          this.generalService.openDialogError(error);
        },
      });
      // console.log(idCourseOffering);
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

  submitDelete(element: HTMLInputElement) {
    let deletebox: any[] = Array.from(
      document.getElementsByClassName('deletebox')
    );
    let listIdCourse: string[] = [];
    deletebox.forEach((x) => {
      if (x.checked == true) {
        listIdCourse.push(x.value);
      }
    });
    this.courseManageService
      .deleteCourseRegisterForProfessor(listIdCourse)
      .subscribe({
        next: (x: any) => {
          element.checked = false;
          this.getListSubjectForProfressor();
          this.getListFake();
        },
        error: (error) => {
          this.generalService.openDialogError(error);
        },
      });
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

}
