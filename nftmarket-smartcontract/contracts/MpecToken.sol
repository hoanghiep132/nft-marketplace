pragma solidity ^0.8.3;
import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract MpecToken is ERC20{

    constructor() ERC20("Mpec Token", "MPECT") payable{
        _mint(msg.sender, 100_000_000_000_000_000_000_000_000);
    }

    function getBalanceOf(address account) view external returns (uint256) {
        return balanceOf(account);
    }

    function transferToken( address to, uint256 amount) external {
        amount = amount * 1_000_000_000_000_000_000;
        transfer(to, amount);
    }

    function approveToken(address to, uint amount) external{
        amount = amount * 1_000_000_000_000_000_000;
        approve(to, amount);
    }

    function getTotalSupply() external view returns (uint){
        return totalSupply();
    }
}