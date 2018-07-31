//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
 
  onLoad: function () {
    console.log("index->onLoad")
      this.setData({
        motto: app.globalData
      })
  },
  onReady: function () {
    console.log("index->onReady")
  },
  onShow: function () {
    console.log("index->onShow")
  },
  onHide: function () {
    console.log("index->onHide")
  },
  onUnload: function () {
    console.log("index->onUnload")
  },
  clickMe: function(){
    // wx.navigateTo({
    //   url: '../test/test',
    // })
    wx.redirectTo({
      url: '../test/test',
    })
  }
})
