import {FormGroup, FormBuilder, FormArray} from '@angular/forms';
import {Component, OnInit, Inject} from '@angular/core';
import {SectionModel} from '../../models/section-model';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {Observable} from 'rxjs';
import {AngularFirestore} from '@angular/fire/firestore';
import {PortfolioDataService} from '../../services/portfolio-data.service';

@Component({
  selector: 'section-choice',
  templateUrl: './section-choice.component.html',
  styleUrls: ['./section-choice.component.scss']
})
export class SectionChoiceComponent implements OnInit {
  selectedTemplate: SectionModel;
  formTemplate: FormGroup;
  templatePipe: Observable<SectionModel[]>;

  constructor(private fb: FormBuilder, private portfolioService: PortfolioDataService,
              public dialogRef: MatDialogRef<SectionChoiceComponent>, private db: AngularFirestore,
              @Inject(MAT_DIALOG_DATA) public data) {

    this.templatePipe = db.collection('/sections').valueChanges() as Observable<SectionModel[]>;
  }

  ngOnInit() {
    this.dialogRef.backdropClick().subscribe(event => {
      if (confirm('Are you sure to about this?')) {
        this.dialogRef.close();
      }
    })
  }

  selectTemplate(template: SectionModel) {
    this.selectedTemplate = template;
  }

  putTemplate() {
    this.formTemplate = this.portfolioService.createSectionForm(this.selectedTemplate);
  }

  isSelected(template): boolean {
    if (!this.selectedTemplate) {
      return false;
    }
    return template.sectionImage === this.selectedTemplate.sectionImage;
  }

  saveSection() {
    this.portfolioService.submitImages();
    this.dialogRef.close(this.formTemplate);
  }
}
