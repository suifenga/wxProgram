// pages/mine/mine.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    faceImage: "../../resource/images/noneface.png",
    nickname: "昵称",
    fansCounts: 0,
    followCounts: 0,
    receiveLikeCounts: 0,
  },
  /**
   * 用户注销
   */
  logout:function(e){
    var user = app.getGlobalUserInfo();
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
          // app.userInfo = null;
          wx.removeStorageSync("userInfo");
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
    // var user = app.userInfo;
    var user = app.getGlobalUserInfo();
    var me = this;
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (tempFilePaths.length>0){
          console.log(tempFilePaths[0]);
              wx.uploadFile({
                url: app.serverUrl + "/user/uploadFace?userId=" + user.id, //仅为示例，非真实的接口地址
                filePath: tempFilePaths[0],
                name: 'file',
                success: function (res) {
                  var data = JSON.parse(res.data);
                  console.log(data);
                   wx.hideLoading();
                  if (data.status == 200) {
                    wx.showToast({
                      title: "用户上传成功~！",
                      icon: 'none',
                      duration: 3000
                    })
                    me.setData({
                      faceUrl: app.serverUrl+data.data
                    })
                  

                  } else if (data.status == 500) {
                    wx.showToast({
                      title: data.msg,
                      icon: 'none',
                      duration: 3000
                    })
                  }
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
    var me = this;
    var userInfo = app.getGlobalUserInfo();
    wx.showLoading({
      title: '正在获取用户信息。。。'
    });
    wx.request({
      url: app.serverUrl + "/user/queryByUserId?userId=" + userInfo.id,
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data);
        var status = res.data.status;
        var userInfo = res.data.data;
        wx.hideLoading();
        var faceImage = me.data.faceUrl;
        if (userInfo.faceImage != null && userInfo.faceImage != '' && userInfo.faceImage!=undefined){
          faceImage = app.serverUrl +userInfo.faceImage;
        }
        me.setData({
          faceImage: faceImage,
          fansCounts: userInfo.fansCounts,
          followCounts: userInfo.followCounts,
          receiveLikeCounts: userInfo.receiveLikeCounts,
          nickname: userInfo.nickname
        })
      }
    })
  },

  uploadVideo:function(e){
    var me = this
    wx.chooseVideo({
      sourceType: ['album', 'camera'],
      success: function (res) {
        console.log(res);
        var tempDuration = res.duration;
        var tempHeight = res.height;
        var tempWidth = res.width;
        var tempSize = res.size;
        var tempFilePath = res.tempFilePath;
        var thumbTempFilePath = res.thumbTempFilePath;
        if (tempDuration>20){
          wx.showToast({
            title: "视频太长了老铁不稳~",
            icon: 'none',
            duration: 3000
          })
        } else if (tempDuration <5){
          wx.showToast({
            title: "视频太短了不到5秒。老铁不稳~",
            icon: 'none',
            duration: 3000
          })
        } else{
          wx.navigateTo({
            url: '../chooseBgm/chooseBgm?tempDuration=' + tempDuration
              + '&tempHeight=' + tempHeight
              + '&tempWidth=' + tempWidth
              + '&tempSize=' + tempSize
              + '&tempFilePath=' + tempFilePath
              + '&thumbTempFilePath=' + thumbTempFilePath
          })
        }
      }
    })
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