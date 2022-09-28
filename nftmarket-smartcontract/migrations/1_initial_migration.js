// const MpecToken = artifacts.require("MpecToken");
// const NFTDefaultCollection = artifacts.require("NFTDefaultCollection");
// const NFTCollectionFactory = artifacts.require("NFTCollectionFactory");

const NFTMarketplace = artifacts.require("NFTMarketPlace");
// const NFTAuction = artifacts.require("NFTAuction");

module.exports = function (deployer) {
  // deployer.deploy(MpecToken, 100_000_000_000_000_000_000_000_000);
  // deployer.deploy(MpecToken);
  // deployer.deploy(NFTDefaultCollection,"Mpec Collection", "MPEC");
  // deployer.deploy(NFTCollectionFactory);

  deployer.deploy(NFTMarketplace, "0xA867532a418cA8Af7034838e5e8d275450F27FF9");

  // deployer.deploy(NFTAuction, "0xA867532a418cA8Af7034838e5e8d275450F27FF9");
};
