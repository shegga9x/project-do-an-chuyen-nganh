import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'listFilter' })
export class ListFilterPipe implements PipeTransform {

    transform(list: any[], keyword: any, properties: string[]): any[] {
        if (!list) return [];
        if (!keyword) return list;
        let listReturn: any[] = []
        for (let index = 0; index < properties.length; index++) {
            try {
                list.filter(item => item[properties[index]].toString().search(new RegExp(keyword, 'i')) > -1).forEach(element => {
                    listReturn.push(element)
                });
            } catch (error) {
            }
        }
        var unique = listReturn.filter(function (elem, index, self) {
            return index === self.indexOf(elem);
        })
        return unique;
    }


}
