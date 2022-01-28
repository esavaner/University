export interface ChatModel {
  count: number;
  messages: {
    content: string;
    createdAt: number,
    uid: string
  }[];
  users: string[];
  unseen: {};
}
