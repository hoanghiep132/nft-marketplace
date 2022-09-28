pragma solidity ^0.8.3;
import "@openzeppelin/contracts/utils/Counters.sol";
import "./MpecToken.sol";
import "./NFTCollection.sol";

contract NFTAuction {

    using Counters for Counters.Counter;
    Counters.Counter public auctionIds;

    mapping(uint => Auction) public auctions;

    mapping(uint => BidAction[]) public bidHistory;

    address private owner;

    address private mpecTokenAddress;

    constructor(address _mpecTokenAddress){
        mpecTokenAddress = _mpecTokenAddress;
        owner = msg.sender;
    }

    event CreateAuction(address nftContract, uint tokenId, uint startTime, uint endTime, uint startPrice, uint currTokenId);

    struct Auction{
        address nftContract;
        uint tokenId;
        uint startTime;
        uint endTime;
        uint highestBid;
        address highestBidder;
        address seller;
        uint status;
    }

    struct BidAction{
        address bidder;
        uint value;
    }


    function createAuction(
        address nftContract,
        uint tokenId,
        uint startTime,
        uint startPrice,
        uint endTime
        ) public returns (uint){

        require(startPrice > 0, "Price must be at least 1 wei");

        require(endTime > startTime, "EndTime is invalid");

        uint currentAuctionId = auctionIds.current();

        Auction memory auction = Auction(nftContract, tokenId, startTime, endTime, startPrice, address(0), msg.sender, 1);

        auctions[currentAuctionId] = auction;

        auctionIds.increment();

        NFTCollection collection = NFTCollection(nftContract);
        collection.transferFrom(msg.sender, address(this), tokenId);

        emit CreateAuction(nftContract, tokenId, startTime, endTime, startPrice, currentAuctionId);

        return currentAuctionId;
    }

    function cancelAuction(uint _auctionId) public {
        Auction memory auction = auctions[_auctionId];

        require(auction.status == 1, "This auction is not avaiable");

        require(auction.seller == msg.sender, "User dont have authorize to cancel auction for this item");
    
        auctions[_auctionId].status = 0;
    }

    function endAuction(uint _auctionId) public{

        require(msg.sender == owner, "User dont have authorize to end auction for this item");

        Auction memory auction = auctions[_auctionId];

        require(auction.status == 1, "This auction is not avaiable");

        // check balance và chuyển tiền
        uint price = auctions[_auctionId].highestBid;
        address highestBidder = auctions[_auctionId].highestBidder;
        address seller = auctions[_auctionId].seller;

        NFTCollection collection = NFTCollection(auctions[_auctionId].nftContract);
        collection.transferFrom(address(this), highestBidder, auctions[_auctionId].tokenId);

        MpecToken mpecToken = MpecToken(mpecTokenAddress);
        mpecToken.transfer(seller, price);

        BidAction[] memory bidActions = bidHistory[_auctionId];
        for(uint i = 0; i < bidActions.length; i++){
            if(bidActions[i].bidder == highestBidder){
                continue;
            }
            mpecToken.transfer(bidActions[i].bidder, bidActions[i].value);
        }

        auctions[_auctionId].status = 0;
    }

    function bid(uint _auctionId, uint value) payable public{
        Auction memory auction = auctions[_auctionId];

        require(auction.status == 1, "This auction is not avaiable");
        require(block.timestamp >= auction.startTime, "This auction have not yet started");
        require(block.timestamp <= auction.endTime, "This auction is ended");

        require(value > auction.highestBid, "New price must higher last price");

        auctions[_auctionId].highestBid = value;
        auctions[_auctionId].highestBidder = msg.sender;

        BidAction[] storage bidActions = bidHistory[_auctionId];

        bool existed = false;
        uint transferValue = value;
        for(uint i = 0; i < bidActions.length; i++){
            if(bidActions[i].bidder == msg.sender){
                existed = true;
                uint currBid = bidActions[i].value;
                transferValue = value - currBid;
                bidActions[i].value = value;
            }
        }

        if(!existed){
            bidActions.push(BidAction(msg.sender, value));
        }

        MpecToken mpecToken = MpecToken(mpecTokenAddress);
        mpecToken.transferFrom(msg.sender, address(this), transferValue);

    }
}