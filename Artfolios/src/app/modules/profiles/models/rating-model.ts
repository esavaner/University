export interface RatingModel {
  rating: number;
  votes: {
    voterId: string,
    vote: number
  }[];
}
