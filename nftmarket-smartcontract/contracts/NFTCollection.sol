pragma solidity ^0.8.3;
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract NFTCollection is ERC721URIStorage {    

    using Counters for Counters.Counter;
    Counters.Counter private tokenIds;

    event mintNft(string tokenURI, uint tokenId);

    address private owner;

    address private factory;

    constructor(address _owner, string memory _name, string memory _symbol) ERC721(_name, _symbol) {
        owner = _owner;
        factory = msg.sender;
    }

    function mint(string memory tokenURI) public returns (uint) {

        require(msg.sender == owner, "User dont have permission to mint NFT in this collection");

        uint currentId = tokenIds.current();

        _safeMint(msg.sender, currentId);
        _setTokenURI(currentId, tokenURI);

        emit mintNft(tokenURI, currentId);

        tokenIds.increment();
        return currentId;
    }
}