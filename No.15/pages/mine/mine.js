// pages/mine/mine.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    faceUrl: "../../resource/images/noneface.png",
    nickname: "昵称",
    fansCounts: 0,
    followCounts: 0,
    receiveLikeCounts: 0,
  },
  logout:function(e){
    var user = app.userInfo;
    wx.showLoading({
      title: '正在注销中。。。'
    });
    wx.request({
      url: app.serverUrl + "/logout?userId="+user.id,
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data);
        var status = res.data.status;
        wx.hideLoading();
        if (status == 200) {
          wx.showToast({
            title: "用户注销成功~！",
            icon: 'none',
            duration: 3000
          })
          app.userInfo = null;
          wx.redirectTo({
            url: '../userRegister/userRegister',
          })

        } else if (status == 500) {
          wx.showToast({
            title: res.data.msg,
            icon: 'none',
            duration: 3000
          })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})