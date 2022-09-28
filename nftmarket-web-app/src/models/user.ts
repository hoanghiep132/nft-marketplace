export interface UserEntity{
  id:string;
  fullName:string;
  username:string;
  email?:string;
  phone?:string;
  active?: boolean;
}
