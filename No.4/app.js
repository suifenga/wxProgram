//app.js
App({
  onLaunch: function (options) {
    debugger
    console.log("onLaunch")
  },
  onShow: function (options) {
    debugger
    console.log("onShow")
  },
  onHide: function () {
    debugger
    console.log("onHide")
  },
  onError: function (msg) {
    debugger
    console.log("onError")
  },
  globalData: 'I am global data'
})