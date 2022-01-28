export class SectionModel {
  fields: FieldModel[];
  titleValue: string;
  titleHide: boolean;
  sectionImage?: string;
  headingColor?: string;
}

export class FieldModel {
  type: TypeOfField;
  value: string;
  order: number; // lub kolejność w tablicy (?)
  width: number; // Procenty
  offset: number; // Offeset tylko z lewej
  style: string;
  class: string;
}

export enum TypeOfField {
  SIMPLETEXT = 0,
  IMAGE = 1,
  YTLINK = 2,
}
