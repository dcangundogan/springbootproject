import { Permission } from './permission.model';

export interface Role {
  id: string;
  rolename: string;
  permissions: Permission[];
}
