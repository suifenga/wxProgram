//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'test2',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
 
  onLoad: function () {
    console.log("test->onLoad")
  },
  onReady: function () {
    console.log("test->onReady")
  },
  onShow: function () {
    console.log("test->onShow")
  },
  onHide: function () {
    console.log("test->onHide")
  },
  onUnload: function () {
    console.log("test->onUnload")
  },
})
