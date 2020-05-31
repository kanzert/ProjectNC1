export interface UserInvite {
  id?: number;
  inviteText: string;
  userIdFrom: number;
  userIdTo: number;
  usernameFrom: string;
  image?: string;
}
