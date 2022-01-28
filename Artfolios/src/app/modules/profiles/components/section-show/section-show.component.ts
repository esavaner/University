import {FormGroup, FormArray, FormControl} from '@angular/forms';
import {Component, OnInit, Input} from '@angular/core';
import {FieldModel, SectionModel, TypeOfField} from '../../models/section-model';
import {AuthService} from '../../../../auth.service';


@Component({
  selector: 'section-show',
  templateUrl: './section-show.component.html',
  styleUrls: ['./section-show.component.scss']
})
export class SectionShowComponent implements OnInit {
  @Input('parentForm') parentForm: FormGroup;
  @Input('section') section: SectionModel;
  typeOfField = TypeOfField;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
  }

  get title(): FormGroup {
    return this.parentForm.get('titleValue') as FormGroup;
  }

  get fields(): FormArray {
    return this.parentForm.get('fields') as FormArray;
  }

  get headingColor(): string {
    const heading = this.parentForm.get('headingColor') as FormGroup;
    let color: string;
    if (heading) {
      color = heading.value;
    } else {
      color = '#000';
    }
    return color;
  }
}
