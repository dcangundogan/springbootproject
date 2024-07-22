export interface User {
  id: string;
  name: string;
  surname: string;
  email: string;
  identity_number: string;
  birth_date: Date;
  salary: number;
  roles: string[];
  createdAt: Date;
  updatedAt: Date;
  [key: string]: any; // This line adds an index signature
}
