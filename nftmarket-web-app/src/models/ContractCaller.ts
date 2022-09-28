
export class ContractCaller{

    private static instance: ContractCaller;

    private constructor() {
    }

    public static getInstance(): ContractCaller{
        if(!this.instance){
            this.instance = new ContractCaller();
        }
        return this.instance;
    }
}