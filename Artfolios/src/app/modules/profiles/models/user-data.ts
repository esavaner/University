export interface UserData {
  firstName: string;
  lastName: string;
  address: {
    street: string;
    city: string;
    postalCode: string;
  };
  dateOfBirth: string;
  privateAccount: boolean;
  gender: string;
  hasPortfolio: boolean;
  chats?: {id: string; user: string}[];
}
