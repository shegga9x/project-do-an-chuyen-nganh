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
    //this.getSemesterReuslt();
  }

  // change listSubAvailable in courseManagerServices to empty when redirect to other page
  ngOnDestroy(): void {
    this.courseManageService.listSubAvaliable = [];
  }

  updateList( idSchedule: string, checked: HTMLInputElement): void {
    this.listCourseRegistRequests.set(idSchedule, checked.checked);
  }

  submit() {
    this.courseManageService.submitCourseRegist(this.listCourseRegistRequests);
  }
  getSubAvailable() {
    this.courseManageService.getSubAvailableRegist().subscribe({
      next: (x: any) => {
        //foreach
        x.forEach((element: any) => {
          console.log(element);
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

  filterSubAvaiable(idMonHoc: string) {
    if (idMonHoc == '') {
      this.listSubAvailable = this.courseManageService.listSubAvaliable;
      return;
    }
    this.listSubAvailable = this.listSubAvailable.filter(
      (x) => x['courseDTO'].idCourse == idMonHoc
    );
  }

  // getSemesterReuslt() {
  //   this.courseManageService
  //     .getSemesterReusltRegist()
  //     .subscribe((x) => console.log(x));
  // }

}
