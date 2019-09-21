import {Injectable} from "@angular/core";
import * as moment from 'moment';


@Injectable({
  providedIn: 'root'
})
export class DateUtils {
  // noinspection JSMethodCanBeStatic
  convertDateTimeFromServer(date: any): Date {
    if (date) {
      return new Date(date);
    }
    else {
      return null;
    }
  };

  convertDateToServerString(date: Date): string {
    return moment(date).utc(false).format('YYYY-MM-DD HH-mm-ss');
  }
}
