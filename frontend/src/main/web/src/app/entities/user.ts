export interface User {
  id: string;
  firstName: string;
  lastName: string;
  username: string;
  password?: string;
  email: string;
  role: {id?: string, name: string};
  status?: {
    id: number,
    name: string
  };
  image?: string;
  token?: string;
  joined?: boolean;
}
