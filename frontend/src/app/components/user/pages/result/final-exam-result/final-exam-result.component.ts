import { Component, ViewChild, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
import { EntityLoadReponse } from './../../../../../models/entityLoadReponse';
import { PDTService } from './../../../../../services/pdt.service';


@Component({
  selector: 'app-final-exam-result',
  templateUrl: './final-exam-result.component.html',
  styleUrls: ['./final-exam-result.component.scss']
})
export class FinalExamResultComponent implements OnInit {
  entityLoadReponse: EntityLoadReponse;
  finish: boolean = false;
  firstFieldName: string;
  entityClass: string;
  linkEntityFieldName: string[] = [];
  fieldNameOfEntity: string[] = [];
  listRequest = new Map();
  searchTextChanged = new Subject<any>();
  listRecord: any[] = [];
  searchTerm: string;
  error: '';

  constructor(private titleService: Title, private pDTService: PDTService) {
    this.titleService.setTitle("Final Exam Result");
    this.searchTextChanged
      .pipe(
        debounceTime(1000),
        distinctUntilChanged((previous: any, current: any) => {
          return previous.value === current.value
        })
      )
      .subscribe(res => {
        this.onChangeValue(res.param, res.evt.target.id)
      });
  }

  ngOnInit(): void {
    this.entityClass = "Account"

    this.loadEntity();
  }
  loadEntity() {
    this.pDTService.loadEntity(this.entityClass).then(x => {
      this.entityLoadReponse = x;
      this.firstFieldName = Object.keys(this.entityLoadReponse.record)[0]
      this.linkEntityFieldName = Object.keys(this.entityLoadReponse.listLinkedEntity);
      this.fieldNameOfEntity = this.entityLoadReponse.listEntitiesVariableAfter;
      this.listRecord = [];
      for (let index = 0; index < this.entityLoadReponse.record[this.firstFieldName].length; index++) {
        let record: any = {};
        this.fieldNameOfEntity.forEach(element => {
          Object.assign(record, { [element]: this.entityLoadReponse.record[element][index] });
        })
        this.listRecord.push(record);
      }
      this.finish = true;
    });
  }
  onSelectEntityClass(): void {
    this.listRequest.clear();
    this.loadEntity();
  }
  listCheckAdd(id: any) {
    const map1 = new Map();
    if (this.listRequest.get(id) != null) {
      this.listRequest.delete(id);
    } else {
      this.fieldNameOfEntity.forEach(element => {
        map1.set(element, (<HTMLInputElement>document.getElementById(element + id)).value)
      });
      const obj = Object.fromEntries(map1);
      this.listRequest.set(id, obj);
    }
  }
  onChangeValue(id: any, position: any) {
    const map1 = new Map();
    this.fieldNameOfEntity.forEach(element => {
      const value = (<HTMLInputElement>document.getElementById(element + id)).value
      map1.set(element, value == "" ? null : value)
    });
    const obj = Object.fromEntries(map1);
    const objJSON = JSON.stringify(obj);
    const request = { entityClass: this.entityClass, jsonObject: objJSON };
    if (id < 0)
      this.pDTService.addEntity(request).then(x => console.log(x));
    else this.pDTService.updateEntity(request).then(x => { this.colorChange(position, 1, "ok"); }).catch(err => { this.colorChange(position, 0, err) });
  }
  deleteSubmit() {
    const listObject: any[] = [];
    this.listRequest.forEach(element => {
      for (let index = 0; index < this.entityLoadReponse.keyCount; index++) {
        if (index < this.entityLoadReponse.keyCount) {
          let object = { [this.entityLoadReponse.listEntitiesVariableAfter[index]]: element[this.entityLoadReponse.listEntitiesVariableAfter[index]] };
          listObject.push(object);
        }
      }
    });
    const fristFieldType = this.entityLoadReponse.listEntityJavaType[this.entityLoadReponse.listEntitiesVariableAfter[0]] == "number" ? "java.lang.Integer" : "java.lang.String"
    const request = {
      entityClass: (this.entityLoadReponse.keyCount == 1 ? fristFieldType : this.entityClass + "Id")
      , entityParent: this.entityClass
      , jsonObject: JSON.stringify(listObject)
    };
    this.pDTService.deleteEntity(request).then(x => console.log(x));
  }
  inputFn(evt: any, param: any) {
    this.searchTextChanged.next({ evt, param, value: evt.target.value });
  }
  search(value: string): void {
    this.listRecord = this.listRecord.filter((val) => val[this.firstFieldName].toLowerCase().includes(value));
  }
  colorChange(position: any, type: number, mess: any) {
    if (position != 0) {
      console.log(position)
      const doc = document.getElementById(position);
      const backgroundColor = ["#FFBABA", "#88B04B"];
      const color = ["#D8000C", "#fff"];
      doc!.style.backgroundColor = backgroundColor[type];
      doc!.style.color = color[type];
      this.error = mess == "ok" ? '' : mess;
      if (type != 0) {
        setTimeout(() => {
          this.error = '';
          doc!.style.backgroundColor = 'white';
          doc!.style.color = 'black';
        }, 2000);
      }

    }

  }

}


