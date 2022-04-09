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

  loading: boolean = false;
  listCourseRegistRequests: Map<string, boolean> = new Map<string, boolean>();
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
    this.courseManageService.listSubAvaliable = [];
  }

  updateListAndSaveToDB(idCourseOffering: string, checked: HTMLInputElement): void {

    this.listCourseRegistRequests.set(idCourseOffering, checked.checked);
    checked.disabled = true;
    this.courseManageService.submitCourseRegisterFake(idCourseOffering).subscribe(() => {
      this.courseManageService.getCourseRegisterFake().subscribe((x: any) => {
        this.listCourseRegisterFake = x;
      });
    });
  }

  submit() {
    this.courseManageService.submitCourseRegist(this.listCourseRegistRequests).subscribe(x => { (x) });
  }

  getSubAvailable() {
    this.courseManageService.getSubAvailableRegist().subscribe({
      next: (x: any) => {
        //foreach
        x.forEach((element: any) => {
          this.listSubAvailable.push(element);
        });
        //add to course-manage services
        this.courseManageService.listSubAvaliable = this.listSubAvailable;
        this.loading = true;
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
      this.listSubAvailable = this.courseManageService.listSubAvaliable;
      return;
    }
    this.listSubAvailable = this.courseManageService.listSubAvaliable.filter(
      (x) => x['courseDTO'].idCourse == idMonHoc
    );
  }

  // getSemesterReuslt() {
  //   this.courseManageService
  //     .getSemesterReusltRegist()
  //     .subscribe((x) => console.log(x));
  // }

}
