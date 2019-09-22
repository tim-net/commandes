import {AbstractControl} from "@angular/forms";

export function validateLines(control: AbstractControl) {
  if(control.value.length == 0) {
   return {linesInValid: true};
  }
}
