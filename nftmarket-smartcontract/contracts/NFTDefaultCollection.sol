pragma solidity ^0.8.3;
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract NFTDefaultCollection is ERC721URIStorage {    

    using Counters for Counters.Counter;
    Counters.Counter private tokenIds;

    address private factory;

    event mintNft(address sender, string tokenURI, uint tokenId);

    constructor(string memory _name, string memory _symbol) ERC721(_name, _symbol) {
        factory = msg.sender;
    }

    function mint(string memory tokenURI) public returns (uint) {

        uint currentId = tokenIds.current();

        _safeMint(msg.sender, currentId);
        _setTokenURI(currentId, tokenURI);

        emit mintNft(msg.sender, tokenURI, currentId);

        tokenIds.increment();
        return currentId;
    }

    function transferItem(address from, address to, uint tokenId) public{
        transferFrom(from, to, tokenId);
    }
}