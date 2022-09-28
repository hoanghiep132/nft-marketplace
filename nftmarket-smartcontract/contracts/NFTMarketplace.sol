pragma solidity ^0.8.3;
import "@openzeppelin/contracts/utils/Counters.sol";
import "./NFTCollection.sol";
import "./MpecToken.sol";

contract NFTMarketPlace{

    uint private constant MINTED = 0;
    uint private constant LISTING = 1;

    address private owner;

    address private mpecTokenAddress;

    using Counters for Counters.Counter;
    Counters.Counter private itemIds;

    struct NftItem {
        uint itemId;
        uint tokenId;
        address nftContract;
        uint price;
        uint status;
        address payable owner;
        address payable seller;
    }

    event ListItem(uint, address, uint, uint, address);

    event BuyItem(uint, address, uint, address);

    mapping(uint => NftItem) public itemMap;

    constructor(address _mpecTokenAddress){
        owner = msg.sender;
        mpecTokenAddress = _mpecTokenAddress;
    }

    function listItem(
        address nftContract,
        uint tokenId,
        uint price
    ) public payable returns (uint) {
        require(price > 0, "Price must be at leastddddd 1 wei");

        uint currentItemId = itemIds.current();

        itemMap[currentItemId] = NftItem(
            currentItemId,
            tokenId,
            nftContract,
            price,
            LISTING,
            payable(address(0)),
            payable(msg.sender)
        );
        itemIds.increment();

        NFTCollection collection = NFTCollection(nftContract);
        collection.transferFrom(msg.sender, address(this), tokenId);

        emit ListItem(currentItemId, nftContract, tokenId, price, msg.sender);

        return currentItemId;
    }

    function buyItem(uint _itemId) public payable  {

        if(itemMap[_itemId].status != LISTING){
            revert("Item is not for buy");
        }

        // chuyen NFT
        NFTCollection collection = NFTCollection(itemMap[_itemId].nftContract);
        collection.transferFrom(address(this), msg.sender, itemMap[_itemId].tokenId);

        // chuyen token
        MpecToken mpecToken = MpecToken(mpecTokenAddress);
        mpecToken.transferFrom(msg.sender, itemMap[_itemId].seller, itemMap[_itemId].price);
        
        itemMap[_itemId].owner = payable(msg.sender);

        itemMap[_itemId].status = MINTED;

        emit BuyItem(_itemId, itemMap[_itemId].nftContract, itemMap[_itemId].tokenId, msg.sender);
    }

    function cancelSelling(address nftContract, uint _itemId) public {
        
        NftItem memory item = itemMap[_itemId];

        require(item.seller == msg.sender, "User dont have permission to call function");
        require(item.status == LISTING, "This item is not in selling list");

        itemMap[_itemId].status = MINTED;

        NFTCollection collection = NFTCollection(nftContract);
        collection.transferFrom(address(this), itemMap[_itemId].seller, itemMap[_itemId].tokenId);
    }

    function checkIsOwner(address from) public view returns (bool){
        return owner == from;
    }

}