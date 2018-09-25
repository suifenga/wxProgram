var videoUtils = require('../../utils/videoUtils.js')
const app = getApp()
Page({

  data: {
    cover:'cover',
    videoContext:"",
    videoInfo:{},
    videId:'',
    src:'',
    userLikeVideo:false,
    serverUrl:'',
    publisher:[]
  },
  

  showSearch:function(){
    wx.navigateTo({
      url: '../videoSearch/videoSearch',
    })
  },
  onLoad:function(params){
    var me = this;
    me.videoContext = wx.createVideoContext('myVideo', me);
    var videoInfo = JSON.parse(params.videoInfo);
    var videoWidth = videoInfo.videoWidth;
    var videoHeight = videoInfo.videoHeight;
    var cover = 'cover';
    if (videoWidth > videoHeight){
      cover = '';
    }
    me.setData({
      videId: videoInfo.id,
      src: app.serverUrl + videoInfo.videoPath,
      videoInfo: videoInfo,
      cover: cover
    })

    var serverUrl = app.serverUrl;
    var user = app.getGlobalUserInfo();
    var loginUserId = "";
    if (user != null && user != undefined && user != '') {
      loginUserId = user.id;
    }
    wx.request({
      url: serverUrl + '/user/queryPublisher?loginUserId=' + loginUserId + "&videoId=" + videoInfo.id + "&publishUserId=" + videoInfo.userId,
      method: 'POST',
      success: function (res) {
        console.log(res.data);

        var publisher = res.data.data.publisher;
        var userLikeVideo = res.data.data.userLikeVideo;

        me.setData({
          serverUrl: serverUrl,
          publisher: publisher,
          userLikeVideo: userLikeVideo
        });
      }
    })




  },
  showIndex:function(){
    wx.redirectTo({
      url: '../index/index',
    })
  },

  onShow:function(){
    var me = this;
    me.videoContext.play();
  },
  onHide:function(){
    var me = this;
    me.videoContext.pause();
  },
  upload:function(){
 
    var me = this;
    var userInfo = app.getGlobalUserInfo();

    var videoInfo = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

    if (userInfo.id == '' || userInfo.id == undefined) {
      wx.navigateTo({
        url: '../userLogin/userLogin?realUrl=' + realUrl,
      })
    } else {
      videoUtils.uploadVideo();
    }


  },
  showMine: function () {
    var me = this;
    var userInfo = app.getGlobalUserInfo();

    var videoInfo = JSON.parse

    if (userInfo.id == '' || userInfo.id == undefined){
      wx.navigateTo({
        url: '../userLogin/userLogin',
      })
    }else{
      wx.navigateTo({
        url: '../mine/mine',
      })
    }
    
   
  },

  likeVideoOrNot: function () {
    var me = this;
    var userInfo = app.getGlobalUserInfo();


    var videoInfoStr = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfoStr;
    if (userInfo.id == '' || userInfo.id == undefined) {
      wx.navigateTo({
        url: '../userLogin/userLogin?realUrl=' + realUrl,
      })
    } else {
      var videoInfo = me.data.videoInfo;
      var userLikeVideo = me.data.userLikeVideo;
      var url = "/video/userLike?userId=" + userInfo.id + "&videoId=" + videoInfo.id + "&videoCreaterId=" + userLikeVideo.userId;

      if (userLikeVideo){
        var url = "/video/userUnLike?userId=" + userInfo.id + "&videoId=" + videoInfo.id + "&videoCreaterId=" + userLikeVideo.userId;
      }
      wx.showLoading({
        title: '....',
      })
      wx.request({
        url: app.serverUrl + url,
        method: "POST",
        header: {
          'content-type': 'application/json', // 默认值
          'headerUserId': userInfo.id,
          'headerUserToken': userInfo.userToken
        },
        success: function (res) {
          wx.hideLoading();
          me.setData({
            userLikeVideo: !userLikeVideo,
          })
        }
      })

    }


  },
  showPublisher:function(){
    var me = this;
    var userInfo = app.getGlobalUserInfo();


    var videoInfo = me.data.videoInfo;
    var realUrl = '../mine/mine#publisherId@' + videoInfo.userId;
    if (userInfo.id == '' || userInfo.id == undefined) {
      wx.navigateTo({
        url: '../userLogin/userLogin?realUrl=' + realUrl,
      })
    } else {
      wx.navigateTo({
        url: '../mine/mine?publisherId=' + videoInfo.userId,
      })

    }
  },
  shareMe:function(){
    var me = this;
    var user = app.getGlobalUserInfo();
    wx.showActionSheet({
      itemList: ["下载到本地","举报用户","分享到好友"],
      success:function(res){
        if (res.tapIndex==0){

        } else if (res.tapIndex==1){
          // 举报
          var videoInfo = JSON.stringify(me.data.videoInfo);
          var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

          if (user == null || user == undefined || user == '') {
            wx.navigateTo({
              url: '../userLogin/userLogin?realUrl=' + realUrl,
            })
          } else {
            var publishUserId = me.data.videoInfo.userId;
            var videoId = me.data.videoInfo.id;
            var currentUserId = user.id;
            wx.navigateTo({
              url: '../report/report?videoId=' + videoId + "&publishUserId=" + publishUserId
            })
          }

        } else{

        }
      }
    })
  }
})
