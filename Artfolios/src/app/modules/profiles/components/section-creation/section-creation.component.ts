import {TypeOfField} from '../../models/section-model';
import {Component, Input, OnInit, SecurityContext, Inject} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {PortfolioDataService} from '../../services/portfolio-data.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'section-creation',
  templateUrl: './section-creation.component.html',
  styleUrls: ['./section-creation.component.scss']
})
export class SectionCreationComponent implements OnInit {
  @Input('template') template: FormGroup;
  typeOfField = TypeOfField;
  previewUrl: any;
  private fileData: File;
  photosToDelete = [];
  rollbackQueue = [];

  constructor(private portfolioService: PortfolioDataService, public dialogRef: MatDialogRef<SectionCreationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,) {
  }

  ngOnInit() {

    this.portfolioService.clearTempQueue();
    if (this.data.editable) {
      this.template = this.data.section;
    }

    this.dialogRef.backdropClick().subscribe(event => {
      if(!this.data.editable) {
        return;
      }
      if (confirm('Are you sure to about this?')) {
        this.rollback();
        this.dialogRef.close();
      }
    });
  }

  get fields() {
    return this.template.get('fields') as FormArray;
  }

  fileProgress(file: any, field) {
    this.fileData = file.target.files[0] as File;
    this.portfolioService.addImg(field, this.fileData);
    this.preview(field);
  }

  preview(field: FormGroup) {
    const mimeType = this.fileData.type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      const localUrl = reader.result.toString();
      this.previewUrl = localUrl;
      // console.log(reader.result.toString());
      if (this.data.editable) {
        const currentUrl = field.get('value').value as string;
        if (currentUrl !== '' && currentUrl.startsWith('https://')) {
          this.photosToDelete.push(currentUrl);
        }
        this.rollbackQueue.push([field, currentUrl]);
        console.log(this.rollbackQueue);
      }
      field.patchValue({value: localUrl});
    };
    reader.readAsDataURL(this.fileData);
  }


  saveSection() {
    this.portfolioService.submitImages();
    this.photosToDelete.forEach(url => {
      this.portfolioService.pushPhotoToDelete(url);
    });
    this.dialogRef.close(this.template);
  }

  abort() {
    this.rollback();
    this.dialogRef.close();
  }

  rollback() {
    this.rollbackQueue.forEach(roll => {
      const field = roll[0] as FormGroup;
      const url = roll[1] as string;
      field.patchValue({value: url});
    });
  }
}
