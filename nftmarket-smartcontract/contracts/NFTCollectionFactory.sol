pragma solidity ^0.8.3;

import "./NFTCollection.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract NFTCollectionFactory {

    event CreateCollection(address);

    function create(string memory _name, string memory _symbol) public returns (address) {
        NFTCollection newNft = new NFTCollection(msg.sender, _name, _symbol);
        address newCollectionAddress = address(newNft);
        emit CreateCollection(newCollectionAddress);
        return newCollectionAddress;
    }
}