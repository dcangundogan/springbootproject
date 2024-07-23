import { Permission } from './permission.model';

export interface Role {
  id: string;
  roleName: string;
  permissions: Permission[];
}
