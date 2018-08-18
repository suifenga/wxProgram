// pages/radio/radio.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    array: [
      { id: "1001", name: "中国", value: "中国", checked: true, color: 'red', disable: false },
      { id: "1002", name: "美国", value: "美国", checked: false, color: 'black', disable: false },
      { id: "1003", name: "俄国", value: "俄国", checked: false, color: 'blue', disable: false },
      { id: "1004", name: "个人主页：idig8.com", value: "idig8.com", checked: false, color: 'yellow', disable: true }
    ],
    checkRadio:"",
  },
  bindbindChangeRadio:function(e){
      var value = e.detail.value;
      this.setData({
        checkRadio:value
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