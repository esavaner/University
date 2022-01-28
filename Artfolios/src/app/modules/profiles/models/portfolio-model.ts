import {SectionModel} from './section-model';
import {ProfessionModel} from './profession-model';

export class PortfolioModel {
  name: string;
  location: string;
  sections: SectionModel[];
  biography: string;
  profileImage: string;
  backgroundColor: string;
  fontColor: string;
  professions: ProfessionModel[];
  isPrivate: boolean;
}
