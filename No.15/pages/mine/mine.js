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
  /**
   * 用户注销
   */
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
   * 头像上传
   */
  uploadFace:function(e){
    var user = app.userInfo;
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (tempFilePaths.length>0){
          console.log(tempFilePaths[0]);

          wx.chooseImage({
            success: function (res) {
              var tempFilePaths = res.tempFilePaths
              wx.uploadFile({
                url: app.serverUrl + "/user/uploadFace?userId=" + user.id, //仅为示例，非真实的接口地址
                filePath: tempFilePaths[0],
                name: 'file',
                formData: {
                  'user': 'test'
                },
                success: function (res) {
                  var data = res.data
                  //do something
                }
              })
            }
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