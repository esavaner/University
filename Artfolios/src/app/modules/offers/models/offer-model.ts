import {ProfessionModel} from '../../profiles/models/profession-model';
import Timestamp = firebase.firestore.Timestamp;
import * as firebase from 'firebase';

export interface OfferModel {
  id: string;
  title: string;
  city: string;
  startDate: Timestamp;
  endDate: Timestamp;
  description: string;
  price: number;
  profession: ProfessionModel;
  isActive: boolean;
  authorId: string;
}
